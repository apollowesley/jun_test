# Billing types {#concept_cgg_skg_tdb .concept}

ApsaraDB for Redis supports the following billing types:

|Billing type|Description|Cautions|
|------------|-----------|--------|
|Subscription \(annual and monthly\)| -   If you select the subscription billing type, you pay for the instance when creating the instance.
-   If you purchase a monthly-subscription instance for a one-year service period, you will receive a 15% discount.

 | -   During the contract period, you can only upgrade or downgrade the configuration of a subscription instance, but cannot release the instance.
-   You cannot change a subscription instance to a Pay-As-You-Go instance.

 |
|Pay-As-You-Go| -   The billing cycle is an hour. If you use an instance for less than one hour, the system still collects fees for one full hour.
-   If you change the instance capacity within one billing cycle, the system calculates billing based on the largest capacity used during the cycle.

For example, if your instance capacity is 1 GB at 01:10, and changed to 8 GB at 01:20, and then to 2 GB at 01:50, the system calculates billing for the instance based on the 8 GB capacity during the billing cycle between 01:00 and 02:00.

-   The system generates the bill within three hours after the current billing cycle. The specific bill time is subject to the system accounting time.

For example, for the billing cycle between 09:00 and 10:00, the system generates the bill before 11:00.

-   After generating the bill, the system automatically deducts the amount payable from your account balance.

 | -   You can release the instance at any time. There are no charges required for the released instance.
-   You can change a Pay-As-You-Go instance to a subscription instance. The system starts the subscription billing method immediately after the change. For more information about the procedure of changing the billing type, see [ Switch to the Subscription mode](../../../../intl.en-US/User Guide/Manage instances/ Switch to the Subscription mode.md#).

 |

**Note:** 

-   The system calculates billing for an ApsaraDB for Redis instance based on the capacity of the instance, rather than the cache capacity that you actually use.

    For example, you activate a 2 GB ApsaraDB for Redis instance and the instance stores 256 MB data. Then, the system calculates billing for the instance based on the 2 GB capacity.

-   The internal network traffic that an ApsaraDB for Redis instance generates is free of charge. Therefore, the data transmission between an ECS instance and an ApsaraDB for Redis instance is free of charge.

