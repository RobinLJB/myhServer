<table class="table table-bordered">
    <tr>
        <th align="center">编号</th>
        <th align="center">还款期数</th>
        <th align="center">应还日期</th>
         <th align="center">应还总额</th>
        <th align="center">应还本金</th>
        <th align="center">应还利息</th>
        <th align="center">剩余本金</th>
        <th align="center">剩余利息</th>
        <th align="center">状态</th>
    </tr>
    <#list repayList as repay>
    <tr>
        <td align="center">${repay.id}</td>
        <td align="center"><a>${repay.period!''}</a></td>
        <td align="center">${repay.repayDate}</td>
        <td align="center">${repay.principal?number + repay.interest?number}</td>
        <td align="center">${repay.principal}</td>
        <td align="center">${repay.interest}</td>
        <td align="center">${repay.principalBalance!''}</td>
        <td align="center">${repay.interestBalance!''}</td>
        <td align="center"><#if repay.status = "1">待还<#else>已还</#if></td>
    </tr>
    </#list>
</table>