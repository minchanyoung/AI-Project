<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>JSP ì</title>
		<script type="text/javascript">
		function fn_validate(){
			var sendBuffer = document.sendBuffer;
			var prompt = sendBuffer.prompt.value;
		
			if((prompt.length == 0 || prompt == ""))
				alert("�� �� ���");
			else{
				sendBuffer.method="post";
				sendBuffer.action="Chat";
				sendBuffer.submit();
			}
		}
		</script>
	</head>
	<body>
		<form name="sendBuffer" encType="utf-8">
			�ؽ�Ʈ: <input type="text" name="prompt"><br>
			<input type="submit" onClick="fn_validate()" value="����">
			<input type="reset" value="�ٽ��Է�">
		</form>
		<div>
			<canvas id="myChart" width="400" height="400"></canvas>
		</div>>
		
		<script>
			const ctx = document.getElementById("myChart").getContext('2d');
			
			let datasets = [
				{
					label: "Dataset 1",
					data: [10, 20, 30, 40, 50],
				},
				{
					label: "Dataset 2",
					data: [40, 30, 20, 10, 0],
				},
				{
					label: "Dataset 3",
					data: [50, 10, 40, 20, 30],
				},
			]
			
			let myChart = new Chart(ctx, {
				type: "line",
				data:{
					labels: ["Red", "Blue", "Yellow", "Green", "Purple"],
					datasets,
				}
			});
		</script>
	</body>
</html>