package business;

/**
 * User: zjzhai
 * Date: 12/3/13
 * Time: 10:05 AM
 */
public class InvoiceApplicationImpl implements InvoiceApplication {
    @Override
    public Invoice findByInvoiceSn(String invoiceSn) {
        return new Invoice(invoiceSn);
    }
}
