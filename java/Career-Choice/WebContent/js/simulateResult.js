const ResultPageManager = {
    init: function () {
        this.cacheDOMElements();
        this.setupInitialData();
        this.createCharts();
        this.createDistributionCharts();
        this.updateRecommendation();
        this.bindEventListeners();
    },

    cacheDOMElements: function () {
        this.elements = {
            prioritySlider: document.getElementById("prioritySlider"),
            priorityLabel: document.getElementById("priorityLabel"),
            recommendedJobName: document.getElementById("recommendedJobName"),
            recommendationReason: document.getElementById("recommendationReason"),
            incomeChartCanvas: document.getElementById('incomeChart'),
            satisfactionChartCanvas: document.getElementById('satisfactionChart'),
            incomeDistributionChartCanvas: document.getElementById('incomeDistributionChart'),
            satisfactionDistributionChartCanvas: document.getElementById('satisfactionDistributionChart'),
            distributionChartTitle: document.getElementById('distributionChartTitle'),
            adviceForm: document.querySelector('form[action="advice.jsp"]')
        };
    },

    setupInitialData: function () {
        this.scenarios = [
            {
                id: "current",
                name: "현직 유지",
                income: predictionResultsRaw[0].income_change_rate,
                satisfaction: predictionResultsRaw[0].satisfaction_change_score,
                distribution: predictionResultsRaw[0].distribution
            },
            {
                id: "jobA",
                name: jobCategoryMapJs[selectedJobACategory] || "직업 A",
                income: predictionResultsRaw[1].income_change_rate,
                satisfaction: predictionResultsRaw[1].satisfaction_change_score,
                distribution: predictionResultsRaw[1].distribution
            }
        ];

        if (predictionResultsRaw.length > 2 && predictionResultsRaw[2]) {
            this.scenarios.push({
                id: "jobB",
                name: jobCategoryMapJs[selectedJobBCategory] || "직업 B",
                income: predictionResultsRaw[2].income_change_rate,
                satisfaction: predictionResultsRaw[2].satisfaction_change_score,
                distribution: predictionResultsRaw[2].distribution
            });
        }

        this.generateUniqueScenarioNames();
    },

    generateUniqueScenarioNames: function () {
        const nameCount = {};
        this.scenarios = this.scenarios.map((s) => {
            const base = s.name;
            nameCount[base] = (nameCount[base] || 0) + 1;
            const newName = nameCount[base] > 1 ? `${base} #${nameCount[base]}` : base;
            return { ...s, name: newName };
        });
    },

    bindEventListeners: function () {
        this.elements.prioritySlider.addEventListener("input", () => this.updateRecommendation());
        if (this.elements.adviceForm) {
            this.elements.adviceForm.addEventListener("submit", (e) => {
                this.prepareAdviceData();
            });
        }
    },

    prepareAdviceData: function () {
        document.getElementById('hiddenRecommendedJobName').value = this.elements.recommendedJobName.textContent;
        document.getElementById('hiddenRecommendationReason').value = this.elements.recommendationReason.textContent;
    },

    updateRecommendation: function () {
        const incomeWeight = this.elements.prioritySlider.value / 100;
        const satisfactionWeight = 1 - incomeWeight;

        this.elements.priorityLabel.textContent = `소득 ${Math.round(incomeWeight * 100)}% : 만족도 ${Math.round(satisfactionWeight * 100)}%`;

        const minIncome = Math.min(...this.scenarios.map(s => s.income));
        const maxIncome = Math.max(...this.scenarios.map(s => s.income));
        const minSatisfaction = Math.min(...this.scenarios.map(s => s.satisfaction));
        const maxSatisfaction = Math.max(...this.scenarios.map(s => s.satisfaction));

        let bestScenario = null;
        let maxScore = -Infinity;

        this.scenarios.forEach(scenario => {
            const normalizedIncome = this.normalize(scenario.income, minIncome, maxIncome);
            const normalizedSatisfaction = this.normalize(scenario.satisfaction, minSatisfaction, maxSatisfaction);
            const score = (normalizedIncome * incomeWeight) + (normalizedSatisfaction * satisfactionWeight);

            if (score > maxScore) {
                maxScore = score;
                bestScenario = scenario;
            }
        });

        this.elements.recommendedJobName.textContent = bestScenario.name;
        this.updateRecommendationReason(bestScenario, incomeWeight);

        // 모든 카드에서 recommended 클래스 제거
        document.querySelectorAll('.result-card').forEach(card => {
            card.classList.remove('recommended');
        });

        // 추천된 시나리오에 해당하는 카드에 recommended 클래스 추가
        const recommendedCard = document.querySelector(`.result-card[data-scenario-id="${bestScenario.id}"]`);
        if (recommendedCard) {
            recommendedCard.classList.add('recommended');
        }

        this.updateDistributionCharts(bestScenario);
    },

    updateRecommendationReason: function (bestScenario, incomeWeight) {
        const incomeText = `<strong>${(bestScenario.income * 100).toFixed(2)}%</strong>`;
        const satisText = `<strong>${bestScenario.satisfaction.toFixed(2)}점</strong>`;
        let reason = "";

        if (incomeWeight > 0.7) {
            reason = `소득 상승(${incomeText})을 가장 중요하게 고려했을 때 가장 유리한 선택입니다. 이때 예상되는 만족도 변화는 ${satisText}입니다.`;
        } else if (incomeWeight < 0.3) {
            reason = `직무 만족도 향상(${satisText})을 가장 중요하게 고려했을 때 가장 적합한 선택입니다. 이때 예상되는 소득 변화율은 ${incomeText}입니다.`;
        } else {
            reason = `소득과 만족도의 균형을 고려했을 때, 소득(${incomeText}), 만족도(${satisText}) 양쪽에서 가장 안정적인 결과를 보여줍니다.`;
        }
        this.elements.recommendationReason.innerHTML = reason; // innerHTML을 사용하여 strong 태그 렌더링
    },

    normalize: function (value, min, max) {
        if (max === min) return 0.5;
        return (value - min) / (max - min);
    },

    createCharts: function () {
        const labels = this.scenarios.map(s => s.name);
        const incomeData = this.scenarios.map(s => s.income * 100);
        const satisfactionData = this.scenarios.map(s => s.satisfaction);
        const count = this.scenarios.length;

        const bgColors = ['#7CB9E8', '#FFBC42', '#9ADE7B'].slice(0, count);
        const borderColors = ['#5C9BD8', '#DB9A00', '#70BA5F'].slice(0, count);

        const createChart = (canvas, chartLabel, data, bg, border, yAxisLabel) => {
            const dataMin = Math.min(...data);
            const dataMax = Math.max(...data);
            let yMin, yMax;
            const range = dataMax - dataMin;
            if (range === 0) {
                const buffer = Math.abs(dataMax) * 0.2 || 1;
                yMin = dataMin - buffer;
                yMax = dataMax + buffer;
            } else {
                const padding = range * 0.2;
                yMin = dataMin - padding;
                yMax = dataMax + padding;
            }

            new Chart(canvas, {
                type: 'bar',
                data: {
                    labels: labels,
                    datasets: [{
                        label: chartLabel,
                        data: data,
                        backgroundColor: bg,
                        borderColor: border,
                        borderWidth: 1
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    plugins: { legend: { display: false } },
                    scales: {
                        x: {
                            type: 'category',
                            grid: { display: false },
                            ticks: {
                                autoSkip: false,
                                maxRotation: 45,
                                minRotation: 20,
                                callback: function (value) {
                                    const label = this.getLabelForValue(value);
                                    return label.split(' ').join('\n');
                                }
                            }
                        },
                        y: {
                            min: yMin,
                            max: yMax,
                            ticks: {
                                callback: function (value) {
                                    return `${value.toFixed(2)}${yAxisLabel}`;
                                }
                            }
                        }
                    }
                }
            });
        };

        createChart(this.elements.incomeChartCanvas, '월 소득 변화율', incomeData, bgColors, borderColors, '%');
        createChart(this.elements.satisfactionChartCanvas, '직무 만족도 변화', satisfactionData, bgColors, borderColors, '점');
    },

    createDistributionCharts: function () {
        this.distributionCharts = {};
        const createChart = (canvas, label) => {
            return new Chart(canvas, {
                type: 'bar',
                data: {
                    labels: [],
                    datasets: [{
                        label: label,
                        data: [],
                        backgroundColor: '#AED9E0',
                        borderColor: '#7CB9E8',
                        borderWidth: 1
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    plugins: { legend: { display: false } },
                    scales: {
                        y: { title: { display: true, text: '사례 수 (명)' } },
                        x: { title: { display: true, text: '변화량 구간' } }
                    }
                }
            });
        };
        this.distributionCharts.income = createChart(this.elements.incomeDistributionChartCanvas, '소득 변화율 분포');
        this.distributionCharts.satisfaction = createChart(this.elements.satisfactionDistributionChartCanvas, '만족도 변화 분포');
    },

    updateDistributionCharts: function (scenario) {
        const distributionData = scenario.distribution;
        this.elements.distributionChartTitle.textContent = scenario.name;

        if (!distributionData) {
            this.clearDistributionChart('income', '유사 사례가 부족하여 분포를 표시할 수 없습니다.');
            this.clearDistributionChart('satisfaction', '유사 사례가 부족하여 분포를 표시할 수 없습니다.');
            return;
        }

        this.updateSingleDistributionChart(this.distributionCharts.income, distributionData.income, '%');
        this.updateSingleDistributionChart(this.distributionCharts.satisfaction, distributionData.satisfaction, '점');
    },

    updateSingleDistributionChart: function (chart, data, unit) {
        const labels = data.bins.slice(0, -1).map((bin, i) => {
            const nextBin = data.bins[i + 1];
            // 소득 변화율(%)은 소수점 1자리까지, 만족도(점)는 소수점 1자리까지 표시
            const start = unit === '%' ? (bin * 100).toFixed(1) : bin.toFixed(1);
            const end = unit === '%' ? (nextBin * 100).toFixed(1) : nextBin.toFixed(1);
            return `${start}~${end}${unit}`;
        });
        chart.data.labels = labels;
        chart.data.datasets[0].data = data.counts;
        chart.update();
    },

    clearDistributionChart: function (chartKey, message) {
        const chart = this.distributionCharts[chartKey];
        chart.data.labels = [message];
        chart.data.datasets[0].data = [0];
        chart.update();
    }
};

document.addEventListener("DOMContentLoaded", () => ResultPageManager.init());