<#import "/spring.ftl" as spring />
<html>
    <form action="" method="POST">
        Name:
        <@spring.bind "command.name" />
        <input type="text"
            name="${spring.status.expression}"
            value="${spring.status.value?default("")}" /><br>
        <#list spring.status.errorMessages as error> <b>${error}</b> <br> </#list>
        <br>
        <input type="submit" value="submit"/>
    </form>
</html>