#!/bin/bash
# http://www.ibm.com/developerworks/cn/linux/1309_huangwh_linuxshell/
# 不能运行

getExistingPids(){
    selfPid="$1"
    scriptFile="$2"

    echo $selfPid
    ps -ef | grep "/bin/bash ${scriptFile}" | grep -v "grep" | awk "{ if( \$2 != $selfPid ) print \$2 }"
    echo $1
}

doItsTask(){
    echo "Task is now being executed..."
    sleep 20
}

main(){
    selfPid="$$"
    echo $selfPid
    scriptFile="$0"

    #sleep 30

    existingPid=`getExistingPids $selfPid "$scriptFile"`
    echo $existingPid

    if [ ! -z "$existingPid" ]; then
        echo "The script already running, exiting..."
        sleep 30
        exit -1
    fi

    doItsTask

}

main $*
