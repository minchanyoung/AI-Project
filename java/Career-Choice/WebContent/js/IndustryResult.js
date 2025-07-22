const ctx = document.getElementById('industryChart').getContext('2d');
	const industryData = {
	    total: { name: '전체상업', data: [2.8,3.2,3.5,3.1,2.9], color: '#3498db' },
	    agriculture: { name: '농에업', data: [1.2,1.4,1.5,1.3,1.1], color: '#2ecc71' },
	    manufacturing: { name: '제조업', data: [3.0,3.3,3.6,3.2,3.1], color: '#e74c3c' },
	    it: { name: '정보화', data: [4.1,4.5,4.9,4.6,4.3], color: '#9b59b6' }
	};
	
	let selectedIndustries = ['total'];
	let industryChart = new Chart(ctx, {
	    type: 'line',
	    data: { labels: ['2025','2026','2027','2028','2029'], datasets: [] },
	    options: {
	        responsive: true,
	        plugins: { legend: { display: true } },
	        scales: {
	            y: {
	                ticks: { callback: val => val + '%' },
	                min: 0, max: 6
	            }
	        }
	    }
	});
	
	function updateChart() {
	    industryChart.data.datasets = selectedIndustries.map(key => {
        const item = industryData[key];
        return {
            label: item.name,
            data: item.data,
            borderColor: item.color,
            backgroundColor: item.color + '20',
            fill: true,
            tension: 0.3
        };
    });
    industryChart.update();
}

function updateList() {
    const list = document.getElementById('industryList');
    list.innerHTML = '';
    selectedIndustries.forEach(key => {
        if (key === 'total') return;
        const item = document.createElement('div');
        item.className = 'industry-item';
        item.innerHTML = `
            <span>${industryData[key].name}</span>
            <div>
                <span class="industry-color" style="background:${industryData[key].color}"></span>
                <button class="remove-btn" onclick="removeIndustry('${key}')">&times;</button>
            </div>
        `;
        list.appendChild(item);
    });
}

function removeIndustry(key) {
    selectedIndustries = selectedIndustries.filter(k => k !== key);
    updateChart();
    updateList();
}

document.getElementById('addIndustryBtn').addEventListener('click', () => {
    const value = document.getElementById('industrySelect').value;
    if (value && !selectedIndustries.includes(value)) {
        selectedIndustries.push(value);
        updateChart();
        updateList();
    }
});

// Init
updateChart();
updateList();