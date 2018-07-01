<#assign context="${rc.contextPath}">
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="utf-8">
	<title>${SEO_TITLE}</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta http-equiv="keywords" content="${SEO_KEY}">
	<meta http-equiv="description" content="${SEO_DESC}">
	<link rel="shortcut icon" href="${context}/favicon.ico">
	<link href="${context}/favicon.ico" rel="SHORTCUT ICON" type="image/ico">
	<!-- Global styles START -->
	<link href="${context}/asset/public/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="${context}/asset/public/font-awesome/css/font-awesome.min.css" rel="stylesheet">
	<script type="text/javascript" src="${context}/asset/public/plugins/jquery.min.js"></script>
	<script type="text/javascript" src="${context}/asset/public/plugins/jquery-migrate.min.js"></script>
	<script type="text/javascript" src="${context}/asset/public/bootstrap/js/bootstrap.min.js"></script>	
	<style type="text/css">
		input::-webkit-inner-spin-button {
		-webkit-appearance: none;
		}
		input::-webkit-outer-spin-button {
			-webkit-appearance: none;
			}
		input::-moz-inner-spin-button {
			-moz-appearance: none;
			}
		input::-moz-outer-spin-button {
			-moz-appearance: none;
		}
		.wrapper{
			width: 1000px;
		    margin: 0 auto;
		    border: 1px solid #eee;
		    padding: 10px 30px;
		}
	</style>
</head>
<body>
   <div class="wrapper">
   		<div class="head">
	   		<div div="title">
	   			<h2 style="text-align: center;">借款协议</h2>
	   			<p style="text-align: right;"><span> （标的号：HY${loan.id}）</span></p>
	   		</div>
	   		<div>
	   			<p><b>甲方（投资人）：</b></p>
				投资人列表 
				<table class="table table-bordered">
					<thead>
						<tr>
							<td>用户名</td>
							<td>投资金额</td>
							<td>借款期限</td>
							<td>应收本息</td>
						</tr>
					</thead>
					<tbody>
						<#list investorList as investor>
						<tr>
							<td>${investor.username}</td>
							<td>${investor.amount}</td>
							<td>${loan.cycle}个月</td>
							<td>${investor.principal?number + investor.interest?number}</td>
						</tr>
						<#else>
						<tr>
							<td colspan="4">暂无投资人</td>
						</tr>
						</#list>
					</tbody>
				</table>
				<p>注：因计算中存在四舍五入，最后一期应收本息与之前略有不同</p>
	   		</div>
   		</div>
   		<div class="content">
   			<p>乙方（借款人）：${loan.realName!}&nbsp;&nbsp;功德融平台用户名：${loan.username}</p>
			<p>丙方（见证人）：安徽万诺金融信息服务有限公司</p>
			<p>
				鉴于：<br/> 
				1.丙方是一家合法成立并有效存续的有限责任公司，拥有www.gdrgdr.cn网站（以下简称“该网站”）的经营权，提供金融信息咨询服务，为交易提供信息服务；<br/> 
				2.甲方、乙方均已自愿在该网站注册，并承诺所提供信息真实、有效；所有行为均为其本人真实意愿。<br/>
				3.甲方承诺对本协议涉及的借款具有完全的支配能力，是其自有闲散资金，为其合法所得；自愿将资金借给乙方有偿使用。<br/>
				4.乙方有借款需求，自愿借入甲方资金有偿使用，且按照约定按时还款。甲乙双方形成借贷关系。
				各方经协商一致，共同签署遵照履行。<br/>

			</p>
			<p class="part">
				<h3>第一条 借款基本信息</h3>
				<table class="table table-bordered" style="width: 420px;">
					<tr>
						<td>借款本金数额</td>
						<td>${loan.amount}</td>
					</tr>
					<tr>
						<td>月偿还本息数额</td>
						<td></td>
					</tr>
					<tr>
						<td>还款期数</td>
						<td>${loan.cycle}</td>
					</tr>
					<tr>
						<td>还款日</td>
						<td></td>
					</tr>
					<tr>
						<td>还款起止日期</td>
						<td></td>
					</tr>
				</table>
			</p>
			<p class="part">
				<h3>第二条 各方权利和义务</h3>
				<h4>甲方的权利和义务</h4>
				<p>
					权利<br/>
					1.甲方享有按期收取还款及利息的权利。 <br/>
					2.如乙方违约，甲方享有对乙方进行欠款催收的权利。<br/>
					3.如乙方违约，有权要求丙方提供其已获得的乙方信息。<br/>
					4.甲方享有了解其在丙方的信用评审进度及结果的权利。 <br/>
					5.无须通知乙方，甲方可以根据自己的意愿进行本协议下其债权的转让。<br/>
					义务<br/>
					1.甲方应按合同约定的借款日将足额的借款本金支付给乙方。 <br/>
					2.甲方应主动缴纳利息可能带来的税费，对此，丙方不承担任何责任。<br/> 
					3.如乙方不能足额还款，甲方同意各自按照其借出款项比例收取还款。 <br/>
					4.为了保护甲方利益，帮助丙方进行欠款协助催收工作，甲方在此确认已经委托丙方为其进行欠款催收工作，并由甲方直接对催收后果进行负责。<br/>
					5.甲方不得将本协议项下的任何权利义务转让给他方<br/>

				</p>
				<h4>乙方权利和义务</h4>
				<p>
					权利<br/>
					1.乙方享有借入资金的支配使用权；<br/>
					2.乙方享有了解其在丙方的信用评审进度及结果的权利。 <br/>
					义务<br/>
					1.乙方必须按期足额向甲方偿还本金和利息。<br/>
					2.乙方必须按期足额向丙方支付借款服务费用。 <br/>
					3.若甲方债权进行转让，乙方需对债权受让人继续履行本协议下其对甲方的还款义务，不得以未接到债权转让通知为由拒绝履行还款义务。<br/>
					4.乙方所借款项不得用于任何违法用途。 <br/>
					5.乙方不得将本协议项下的任何权利义务转让给他方。 <br/>

				</p>
			</p>
			<p class="part">
				<h3>第三条 借款服务费</h3>
				<p>
					综述：在本协议中，“服务费”是指因丙方提供信用咨询、评估、还款提醒、账户管理、还款特殊情况沟通等系列服务而产生的费用。<br/>
					服务费：乙方需要缴纳居间服务费，借款成功后，由丙方从乙方账户中直接一次性扣除。<br/>
				</p>
			</p>
			<p class="part">
				<h3>第四条 违约责任</h3>
				<p>
					1.合同各方均应严格履行合同义务，非经各方协商一致或依照本协议约定，任何一方不得解除本协议。<br/>
					2.任何一方违约，违约方应承担因违约导致其他各方产生的费用和损失，包括但不限于调查、诉讼费、律师费等，应由违约方承担。<br/>
					3.乙方违约，甲方有权要求乙方立即偿还欠款；丙方有权要求乙方立即偿还拖欠的服务费。 <br/>
					4.乙方发生逾期，除偿还当期本息及服务费，须另外支付罚息及逾期后的服务费。丙方有权将乙方在本网站账户里的任何余款按照本协议第四条第7项的清偿顺序将乙方的余款用于清偿，并要求乙方支付因此产生的相关费用。<br/>
					计算公式为：当期本金+当期利息+逾期罚息+支付相关费用;<br/>
					当期本金+当期利息+当期服务费+（罚息+逾期后的服务费）×逾期天数<br/>
					说明：【逾期天数】指的是从乙方还款逾期之日至乙方全部偿还借款之日之间的时间天数<br/>
					5.逾期罚息计算：如乙方逾期还款，则应按照下述条款向甲方支付逾期罚息，自逾期开始之日，正常利息停止计算。按照下面公式计算罚息：<br/>
					罚息总额 = 逾期本金×对应罚息利率×逾期天数<br/>
					6.清偿顺序：
					（1）根据本协议产生的其他费用（包括但不限于调查费、诉讼费、律师费等）；
					（2）逾期本金；
					（3）当期利息；
					（4）逾期罚息。
					8.如果乙方连续逾期超过三期，或乙方在逾期后出现逃避、拒绝沟通或拒绝承认欠款事实等恶意行为，本协议项下的全部借款本息及借款服务费提前到期，乙方应立即清偿本协议下尚未偿付的全部本金、利息、罚息、借款服务费及根据本协议产生的其他全部费用。<br/>
					9.如果乙方逾期支付任何一期还款超过30天，或乙方在逾期后出现逃避、拒绝沟通或拒绝承认欠款事实等恶意行为，丙方有权将乙方的“逾期记录”记入公民征信系统，丙方不承担任何法律责任；丙方有权将乙方违约失信的相关信息及乙方其他信息向媒体、用人单位、公安机关、检查机关、法律机关披露，丙方不承担任何责任。 <br/>
					10.在乙方还清全部本金、利息、借款服务费、罚息、逾期服务费之前，罚息及逾期服务费的计算不停止。<br/> 
					11.本借款协议中的所有甲方与乙方之间的借款均是相互独立的，一旦乙方逾期未归还借款本息，任何一个甲方有权单独向乙方追索或者提起诉讼。任何一方（甲方、乙方）违约时，丙方均有权对其单独追索或提起诉讼。 <br/>
				</p>
			</p>
			<p class="part">
				<h3>第五条 提前还款</h3>
				<p>
					1.乙方可以随时进行提前还款，还款周期以本协议借款基本信息中约定的单位进行计算。<br/>
					2.提前还款公式如下： 提前还款应还金额 = 当前剩余本金+ 当期利息。
					提前还款应还金额 = 当前剩余本金+ 当期利息<br/>
					3.乙方提前归还部分借款金额不能视为提前还款，仍需支付全部借款利息及账户服务费。提前归还的部分借款可顺位冲抵后期还款。<br/>
					4.任何形式的提前还款不影响丁方向乙方收取在本协议第三条中说明的服务费。 <br/>
				</p>
			</p>
			<p class="part">
				<h3>第六条 法律及争议解决</h3>
				<p>
					本协议的签订、履行、终止、解释均适用中华人民共和国法律，签署地视为安徽合肥地区。
				</p>
			</p>
			<p class="part">
				<h3>第七条 附则</h3>
				<p>
					1.本协议自乙方网站账户收到借款款项时，由系统自动生成，无需任何一方签字，即刻生效，生效时间即为合同签署时间。<br/>
					2.本协议采用电子文本形式制成，并永久保存在丙方为此设立的专用服务器上备查，各方均认可该形式的协议效力。<br/>
					3.本协议签订之日起至借款全部清偿之日止，乙方或甲方的任何与本次借贷有关的信息发生变更时，应在三日内将更新后的信息提供给丙方，否则，由此带来的任何损失由该方承担，丙方不承担任何责任。<br/>
					4.如果本协议中的某些条款违反适用的法律法规，则对应条款将被视为无效，但并不影响本协议其他条款的效力与执行。 <br/>
				</p>
			</p>
   		</div>
   </div>
</body>
</html>