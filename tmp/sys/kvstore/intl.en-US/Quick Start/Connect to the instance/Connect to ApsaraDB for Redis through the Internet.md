# Connect to ApsaraDB for Redis through the Internet {#concept_xcl_p2d_5db .concept}

## Prerequisites { .section}

To access an ApsaraDB for Redis instance from a local host, please configure port forwarding on ECS. However, the following prerequisites must be met:

-   If the Redis instance is in a VPC network, the ECS instance must be in the same VPC network with the Redis instance .
-   If the Redis instance is in the classic network, the ECS instance must be a classic network instance in the same region as the Redis instance.
-   The internal IP address of the ECS instance must be added to the whitelist of the Redis instance. For more information, see [Set IP whitelists](../../../../../intl.en-US/User Guide/Manage instances/Set IP whitelists.md#).
-   ECS security group rules must be configured to allow access from the local host to the ECS instance and the Redis instance to the ECS instance. For more information, see [Add security group rules](../../../../../intl.en-US/Security/Security groups/Add security group rules.md#).

## ECS Windows { .section}

Currently, ApsaraDB for Redis is accessible through ECS Intranet. To locally access ApsaraDB for Redis through a public network, perform port mapping using netsh on the ECS Windows server.

1.  Log on to the ECS Windows server and run the following command in CMD:

    ```
    netsh interface portproxy add v4tov4 listenaddress=<private IP address of ECS> listenport=6379 connectaddress=<connection address of ApsaraDB for Redis> connectport=6379
    ```

    To view all port forwarding rules on the server, run `netsh interface portproxy show all`.

2.  Perform a verification test locally after configuration is complete.

    ![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/3130/15525315601161_en-US.png)

    1.  Run telnet locally to connect to the ECS Windows server. For example, if the public IP address of the ECS Windows server is 1.1.1.1, you can run `telnet 1.1.1.1 6379`.
    2.  After the ECS Windows server is connected, enter the password to connect to ApsaraDB for Redis: `auth <Redis password>`.
    3.  Write and read data to verify the availability of the connection.
    After performing the preceding steps, you can use a local PC or server to connect to port 6379 of the ECS Windows server through the Internet and access ApsaraDB for Redis.

    **Note:** Given that portproxy is provided by Microsoft and is not an open-source software program, please refer to portproxy instructions on netsh or contact Microsoft for help should you have any questions during the configuration. There are also other alternatives for consideration, such as configuring proxy through portmap.

3.  `netsh interface portproxy delete v4tov4 listenaddress=<private IP address of the ECS server> listenport=6379` can be used to delete unnecessary forwarding rules.

## ECS Linux {#section_ipc_mhd_5db .section}

Currently, ApsaraDB for Redis is accessible through ECS Intranet. To locally access ApsaraDB for Redis through a public network, install rinetd on the ECS Linux server to perform forwarding.

1.  Install rinetd on the ECS Linux server.

    ```
     wget http://www.boutell.com/rinetd/http/rinetd.tar.gz&&tar-xvf rinetd.tar.gz&&cd rinetd
                        sed -i 's/65536/65535/g' rinetd.c #Modify the port range.
                        mkdir /usr/man&&make&&make install
    ```

    **Note:** The rinetd installation package obtained from the download URL may be unavailable. You can download the rinetd installation package from other sources.

2.  Open the configuration file rinetd.conf.

    ```
     vi /etc/rinetd.conf
    ```

3.  Add the following content to the configuration file:

    ```
     0.0.0.0 6379 <connection address of the Redis instance> 6379
    logfile /var/log/rinetd.log
    ```

    **Note:** You can run `cat /etc/rinetd.conf` to check whether the configuration file is correctly modified.

    ![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/3130/15525315601164_en-US.png)

4.  Run the following command to start rinetd:

    ```
     rinetd
    ```

    -   You can run `echo rinetd >>/etc/rc.local` to set auto startup for rinetd.
    -   If a binding error is reported, run `pkill rinetd` to terminate the process and run `rinetd` to start the rinetd process.
    -   After rinetd is started normally, run `netstat -anp | grep 6379` to check whether the service works properly.
    ![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/3130/15525315601165_en-US.png)

5.  Perform a verification test locally.
    1.  You can run redis-cli locally to connect to the ECS Linux server for access verification. For example, if the public IP address of the ECS server with rinetd installed is 1.1.1.1, run `redis-cli -h 1.1.1.1`. Alternatively, telnet to port 6379 of the ECS Linux server and perform the verification. For example, if the public IP address of the ECS Linux server is 1.1.1.1, you can run \`telnet 1.1.1.1 6379\`.
    2.  After the ECS Linux server is connected, enter the password to connect to Redis: `auth <Redis password>`.
    3.  Write and read data to verify the availability of the connection.

        ![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/3130/15525315601166_en-US.png)


After performing the preceding steps, you can use a local PC or server to connect to port 6379 of the ECS Linux server through the Internet and access ApsaraDB for Redis.

**Note:** You can use the above scheme to test and use rinetd. rinetd is open source software. Read its official documentation or contact rinetd for support should you have any questions while using it.

