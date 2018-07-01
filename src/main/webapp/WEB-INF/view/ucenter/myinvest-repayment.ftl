<table class="table table-bordered">
    <tr>
        <th>项目名称</th>
        <th>还款期数</th>
        <th>应还日期</th>
        <th>应还本金</th>
        <th>应还利息</th>
        <th>剩余本金</th>
        <th>剩余利息</th>
        <th>状态</th>
    </tr>
    <#list repayList as repay>
    <tr>
        <td align="center">${repay.title}</td>
        <td align="center"><a>${repay.repayPeriod!''}</a></td>
        <td align="center">${repay.repayDate}</td>
        <td align="center">${repay.principal}</td>
        <td align="center">${repay.interest}</td>
        <td align="center">${repay.principal_balance!''}</td>
        <td align="center">${repay.interest_balance!''}</td>
        <td align="center"><#if repay.status = "1">未还<#else>已还</#if></td>
    </tr>
    </#list>
</table>