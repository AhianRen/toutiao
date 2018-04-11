<html>
<body>
<pre>
    Hello FTL
<#--注释-->
    ${value}
    ${value2!"value2"}
    <#if user3??>${value3}</#if>
    ${value4!}
    <#--遍历List colors-->
    ${colors?size}
    colors[1]:${colors[1]}
    方式1
    <#list colors as color>${color}<#sep>,</#list>
    方式2
    <#list colors>
        <#items as color>
            ${color}<#sep>,
        </#items>
    </#list>
    方式3
    ${colors?join(",","None")}
    <#--遍历Map map-->
    ${map["1"]}
    <#list map?keys as key>
        ${key}:${map[key]}
    </#list>
Include:
    <#assign title = "标题">
    <#include "header.ftl">
user:
    ${user.name}
getter:
    ${user.getName()}
    自定义指令：
    <#macro render color index>
        Color By Macro ${color},${index}
    </#macro>
    <#list colors as color>
        <@render color = color index = color?index></@render>
    </#list>

</pre>
</body>
</html>