# cpu
//上下文切换
https://blog.csdn.net/weixin_34345560/article/details/91751943   

# cache/buffer
cache  cpu与内存的缓存
buffer cpu与硬盘的缓存
>都是缓存在内存中

https://blog.csdn.net/weixin_34345560/article/details/89746129

# io
https://www.cnblogs.com/sunsky303/p/11642655.html

# memory
https://www.cnblogs.com/sunsky303/p/11643161.html
https://blog.csdn.net/weixin_34345560/article/details/90123505

# network
https://www.cnblogs.com/sunsky303/p/11643118.html

##osi
https://www.cnblogs.com/sunsky303/p/10647255.html

##tcp
https://www.cnblogs.com/sunsky303/p/10643263.html


https://www.cnblogs.com/sunsky303/p/11194801.html
https://www.cnblogs.com/sunsky303/p/11010155.html


#http
https://www.cnblogs.com/sunsky303/p/10628927.html

https://blog.csdn.net/wangshuminjava/article/details/97617166   //http长连接

#https
https://www.cnblogs.com/sunsky303/p/10628894.html
https://www.cnblogs.com/sunsky303/p/10628865.html
https://www.cnblogs.com/sunsky303/p/11451282.html
https://blog.csdn.net/bbwangj/article/details/80981770
https://blog.csdn.net/bbwangj/article/details/81777831

https://blog.csdn.net/bbwangj/article/details/82818207

##ipv6
https://www.cnblogs.com/sunsky303/p/11050247.html

##心跳
https://www.cnblogs.com/sunsky303/p/10414146.html


## sysctl.conf
https://www.cnblogs.com/sunsky303/p/12290462.html



##dnsmasq
https://www.cnblogs.com/sunsky303/p/9238669.html
https://www.cnblogs.com/sunsky303/p/9238718.html
https://www.cnblogs.com/sunsky303/p/9234276.html



## soft lockup
```
内核软死锁(soft lockup)，bug没有让系统彻底死掉，但锁定若干进程，处于锁死的状态(内核区域的)，大部分情况都是系统占用内存过多导致的内核触发自我保护机制

Soft lockup名称解释：所谓，soft lockup就是说，这个bug没有让系统彻底死机，但是若干个进程（或者kernel thread）被锁死在了某个状态（一般在内核区域），很多情况下这个是由于内核锁的使用的问题。
```

```
Jul  9 11:29:12 general-master-k8s kernel: Hardware name: innotek GmbH VirtualBox/VirtualBox, BIOS VirtualBox 12/01/2006
Jul  9 11:29:12 general-master-k8s kernel: task: ffff8916f0b53150 ti: ffff8917581d4000 task.ti: ffff8917581d4000
Jul  9 11:29:12 general-master-k8s kernel: RIP: 0010:[<ffffffff865163d6>]  [<ffffffff865163d6>] generic_exec_single+0x106/0x1c0
Jul  9 11:29:12 general-master-k8s kernel: RSP: 0018:ffff8917581d7be0  EFLAGS: 00000202
Jul  9 11:29:12 general-master-k8s kernel: RAX: 00000000000008fd RBX: 00000000000000fb RCX: 0000000000000000
Jul  9 11:29:12 general-master-k8s kernel: RDX: 00000000000008fb RSI: 00000000000000fb RDI: 0000000000000286
Jul  9 11:29:12 general-master-k8s kernel: RBP: ffff8917581d7c30 R08: 0000000000000001 R09: 00003ffffffff000
Jul  9 11:29:12 general-master-k8s kernel: R10: ffff8917581f5360 R11: ffff8917581d7d88 R12: 0000000000000000
Jul  9 11:29:12 general-master-k8s kernel: R13: 00000000000008fd R14: 0000000000000001 R15: 00003ffffffff000
Jul  9 11:29:12 general-master-k8s kernel: FS:  00007f24adb3b700(0000) GS:ffff89175fc80000(0000) knlGS:0000000000000000
Jul  9 11:29:12 general-master-k8s kernel: CS:  0010 DS: 0000 ES: 0000 CR0: 0000000080050033
Jul  9 11:29:12 general-master-k8s kernel: CR2: 000000c420093010 CR3: 00000000b0a16000 CR4: 00000000000406e0
Jul  9 11:29:12 general-master-k8s kernel: DR0: 0000000000000000 DR1: 0000000000000000 DR2: 0000000000000000
Jul  9 11:29:12 general-master-k8s kernel: DR3: 0000000000000000 DR6: 00000000fffe0ff0 DR7: 0000000000000400
Jul  9 11:29:12 general-master-k8s kernel: Call Trace:
Jul  9 11:29:12 general-master-k8s kernel: [<ffffffff8647e2b0>] ? leave_mm+0x110/0x110
Jul  9 11:29:12 general-master-k8s kernel: [<ffffffff8647e2b0>] ? leave_mm+0x110/0x110
Jul  9 11:29:12 general-master-k8s kernel: [<ffffffff8647e2b0>] ? leave_mm+0x110/0x110
Jul  9 11:29:12 general-master-k8s kernel: [<ffffffff865164ef>] smp_call_function_single+0x5f/0xa0
Jul  9 11:29:12 general-master-k8s kernel: [<ffffffff86781685>] ? cpumask_next_and+0x35/0x50
Jul  9 11:29:12 general-master-k8s kernel: [<ffffffff86516a9b>] smp_call_function_many+0x22b/0x270
Jul  9 11:29:12 general-master-k8s kernel: [<ffffffff8647e478>] native_flush_tlb_others+0xb8/0xc0
Jul  9 11:29:12 general-master-k8s kernel: [<ffffffff8647e4eb>] flush_tlb_mm_range+0x6b/0x140
Jul  9 11:29:12 general-master-k8s kernel: [<ffffffff865ec096>] tlb_flush_mmu_tlbonly+0xa6/0xc0
Jul  9 11:29:12 general-master-k8s kernel: [<ffffffff865edb5f>] arch_tlb_finish_mmu+0x3f/0x80
Jul  9 11:29:12 general-master-k8s kernel: [<ffffffff865edc83>] tlb_finish_mmu+0x23/0x30
Jul  9 11:29:12 general-master-k8s kernel: [<ffffffff865ea796>] madvise_free_single_vma+0x116/0x180
Jul  9 11:29:12 general-master-k8s kernel: [<ffffffff865eb6d1>] SyS_madvise+0x4d1/0xac0
Jul  9 11:29:12 general-master-k8s kernel: [<ffffffff86b8de21>] ? system_call_after_swapgs+0xae/0x146
Jul  9 11:29:12 general-master-k8s kernel: [<ffffffff86b8de15>] ? system_call_after_swapgs+0xa2/0x146
Jul  9 11:29:12 general-master-k8s kernel: [<ffffffff86b8dede>] system_call_fastpath+0x25/0x2a
Jul  9 11:29:12 general-master-k8s kernel: [<ffffffff86b8de21>] ? system_call_after_swapgs+0xae/0x146
Jul  9 11:29:12 general-master-k8s kernel: Code: 14 c5 20 e8 14 87 48 89 df e8 37 23 28 00 84 c0 75 53 45 85 ed 74 16 f6 43 20 01 74 10 0f 1f 84 00 00 00 00 00 f3 90 f6 43 20 01 <75> f8 31 c0 48 8b 7c 
Jul  9 11:29:40 general-master-k8s kernel: NMI watchdog: BUG: soft lockup - CPU#1 stuck for 23s! [kubelet:2248]  //软锁死内核区域的进程
Jul  9 11:29:40 general-master-k8s kernel: Modules linked in: tcp_diag inet_diag xt_statistic ipt_rpfilter xt_multiport xt_set iptable_mangle iptable_raw ip_set_hash_ip ip_set_hash_net ip_set vxlan ip6
xt_comment ipt_MASQUERADE nf_nat_masquerade_ipv4 nf_conntrack_netlink nfnetlink iptable_nat nf_conntrack_ipv4 nf_defrag_ipv4 nf_nat_ipv4 xt_addrtype iptable_filter xt_conntrack nf_nat nf_conntrack br_nh_clmulni_intel aesni_intel lrw ppdev gf128mul sg glue_helper i2c_piix4 ablk_helper cryptd parport_pc parport pcspkr video ip_tables xfs libcrc32c sr_mod cdrom ata_generic pata_acpi sd_mod crc_t10dif crct10dif_pclmul crct10dif_common
```

```
cat /proc/sys/kernel/watchdog_thresh
0
#临时生效
sysctl -w kernel.watchdog_thresh=30
echo 30 > /proc/sys/kernel/watchdog_thresh 

#永久生效
vi /etc/sysctl.conf
kernel.watchdog_thresh=30
```


<<<<<<< HEAD
## mail warn
/var/spool/mail/root
[link](https://www.jianshu.com/p/0f74c5baa925)

```
mailx-12.5-19.el7.x86_64 : Enhanced implementation of the mailx command
Repo        : base
Matched from:
Filename    : /bin/mail

yum insall -y mailx

#clean mail 
cat /dev/null >/var/spool/mail/root

mail
d 1-$  //删除全部邮件
q   //退出
```
=======

## rpm
https://blog.csdn.net/bbwangj/article/details/80647036
>>>>>>> 8c788b937b5e8131fdb55609a3d1bfd7c3193cac
