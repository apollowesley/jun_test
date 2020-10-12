# View memory usage of all instances under the current account {#concept_cb1_hmr_n2b .concept}

You can call an API operation based on the RAM user information and the region ID to check memory usage of the instances under the current account. The script is as follows:

[get\_used\_memory.zip](http://docs-aliyun.cn-hangzhou.oss.aliyun-inc.com/assets/attach/48181/cn_zh/1532070045933/get_used_memory.zip)

**Note:** The query result is the memory usage during the period 20 minutes before the current time of the instances.

## Procedure {#section_jv3_dnr_n2b .section}

1.  Install the Alibaba Cloud Python SDK dependency package by running the following command:`pip install aliyun-python-sdk-core`.
2.  Run the preceding script file in Python. In the script, the -r parameter is cn-hangzhou by default. Separate multiple region values with commas. The detailed command is as follows:

    ``` {#codeblock_jd6_r3o_tzw}
    python get_used_memory.py -i ****** -s **********-r cn-hangzhou,cn-beijing -o XXX.csv
    ```

    **Note:** You can run the `python get_used_memory.py –h` command to check the usage of each parameter.


