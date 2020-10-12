# Resource type in RAM {#reference_ivb_n42_xdb .reference}

Only one type of ApsaraDB for Redis resources can be authorized in RAM: Instance.

The following table lists the source description method when the resources are authorized using RAM:

|Resource type|Resource Description in Authorization Policy|
|-------------|--------------------------------------------|
|Instance|acs:kvstore:$regionid:$accountid:instance/$instanceid acs:kvstore:$regionid:$accountid:instance/ acs:kvstore:::instance/|

All $regionids must be the ID of a region or `*`. All $instanceids must be the ID of an instance or   `*`. Similarly, $account-id is the numerical ID of your cloud account, which can be replaced by `*`.

