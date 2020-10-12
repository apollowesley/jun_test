# Parse AOF files {#concept_j1z_l2q_bgb .concept}

## Background information {#section_r1t_w2q_bgb .section}

The append only file \(AOF\) is the main Redis persistence option. You can parse AOF files to view the history commands and the records of a specific key.

## Redis persistence options {#section_nxr_vm2_cgb .section}

-   RDB snapshot option: This option performs point-in-time snapshots of your dataset at specified intervals. It encodes the keys and values as Redis strings and stores the strings in RDB files.
-   AOF persistence option: Similar to binlog, append-only files keep a record of data changes that occur by writing each change to the end of the file. The entire dataset can be recovered by replaying the append-only log from the beginning to the end.

## Details of the AOF persistence option {#section_vqn_yn2_cgb .section}

Redis clients communicate with the Redis server using a protocol called REdis Serialization Protocol \(RESP \). RESP can serialize different data types, including:

-   Simple strings:

    A string that starts with the plus sign \(+\) and ends with rn. For example, +OKrn.

-   Error messages:

    A string that starts with the minus sign \(-\) and ends with rn. For example, -ERR Readonlyrn.

-   Integers

    A data structure that starts with a colon \(:\), ends with rn, and has an integer between the beginning and the end. For example, \(:1rn\).

-   Large strings

    A string structure that starts with a dollar sign \($\), followed by the string length \(less than 512 MB\) and rn, and ends with the string content and rn. For example, $0rnrn.

-   Arrays

    A data structure that starts with an asterisk symbol \(\*\), followed by array elements that are separated by rn. The above four data types can be used as array elements. For example, \*1rn$4rnpingrn.


The Redis client sends an array command to the server. The server responds according to the implementation methods of different commands and records the responses in the AOF file.

## Parse append-only files {#section_c5v_1gq_bgb .section}

The following sample parses an append-only file by calling hiredis with Python. The sample code is described as follows:

```
#! /usr/bin/env python

""" A redis appendonly file parser
"""

import logging
import hiredis
import sys

if len(sys.argv) ! = 2:
   print sys.argv[0], 'AOF_file'
   sys.exit()
file = open(sys.argv[1])
line = file.readline()
cur_request = line
while line:
    req_reader = hiredis.Reader()
    req_reader.setmaxbuf(0)
    req_reader.feed(cur_request)
    command = req_reader.gets()
    try:
        if command is not False:
            print command
            cur_request = ''
    except hiredis.ProtocolError:
        print 'protocol error'
    line = file.readline()
    cur_request += line
file.close
```

The result is as follows: After you obtain the following results, you can view the operations related to a certain key at any time.

```
['PEXPIREAT', 'RedisTestLog', '1479541381558']
['SET', 'RedisTestLog', '39124268']
['PEXPIREAT', 'RedisTestLog', '1479973381559']
['HSET', 'RedisTestLogHash', 'RedisHashField', '16']
['PEXPIREAT', 'RedisTestLogHash', '1479973381561']
['SET', 'RedisTestLogString', '79146']
```

