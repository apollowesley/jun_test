# Upgrade and downgrade the configuration {#concept_fm5_lc5_tdb .concept}

ApsaraDB for Redis supports on-demand configuration upgrades and downgrades to help you increase resource utilization and optimize the cost structure.

You can change the configuration of an ApsaraDB for Redis instance at any time according to your business needs. For more information about the procedure, see [Change configuration](../../../../intl.en-US/User Guide/Manage instances/Change configuration.md#). The billing methods for configuration change are described as follows:

-   Pay-As-You-Go

    The system calculates the billing based on the instance configuration that is used when the system generates a billing order.

-   Subscription

    If you upgrade or downgrade the configuration when you renew the instance upon expiration, the system calculates the billing for the instance based on the new configuration and subscription period.

    The billing methods for configuration change for instances that have not expired are described in [Table 1](#table_itr_jfg_ffb).

    |Configuration change|Billing method|
    |--------------------|--------------|
    |Upgrade|Total fee for upgrading the instance configuration = \(Instance daily fee after upgrade - Instance daily fee before upgrade\) × \(Subscription expiration date - Upgrade date\)|
    |Downgrade|Downgrade refund = \(Daily fee of the instance before downgrade - Daily fee of the instance after downgrade\) × \(Subscription expiration date - Downgrade date\)|

    **Note:** 

    -   You cannot change the configuration when an unpaid renewal order exists under the same account.
    -   If the remaining days of service expiration are less than 365 days, the system calculates the fee based on the monthly subscription price. If the remaining days are greater than or equal to 365 days, the system calculates the fee based on the annual subscription price.
    -   The new configuration that you select during service renewal takes effect during the new billing cycle. The system calculates the billing for your instance based on the new configuration and service duration.

