package ua.parser;

public interface XMLConst {
    String ATTR_ID = "id";
    String ATTR_PRIORITY = "priority";
    String TAG_VOUCHERS = "VoucherList";
    String TAG_VOUCHER = "Voucher";
    String TAG_ORDER = "Order";
    String TAG_TOUR_DATE = "Tour_date";
    String TAG_TOUR = "Tour";
    String TAG_PERSON = "Person";
    String TAG_TYPE_FOOD = "type_food";
    String TAG_TYPE_TOUR = "type_tour";
    String TAG_TYPE_TRANSPORT = "type_transport";
    String TAG_STATUS_VOUCHER = "status_voucher";
    String TAG_STATUS_ORDER = "status_order";
    String TAG_ROLE = "role";
    String TAG_PRIORITY = "priority";
    String TAG_CONTACT = "order_contact";
    String TAG_CONTACT_MANAGER = "voucher_contact";

    String TAG_VOUCHER_COUNT = "voucher_count";
    String TAG_VOUCHER_AMOUNT = "amount";
    String TAG_VOUCHER_DATE = "voucher_date";
    String TAG_VOUCHER_DATE_UPDATE = "date_updated_voucher";

    String TAG_ORDER_COUNT = "order_count";
    String TAG_ORDER_DATE = "order_date";
    String TAG_ORDER_DATE_UPDATE = "date_updated_order";

    String TAG_TOURDATE_DATE_ARRIVAL = "date_arrival";
    String TAG_TOURDATE_PRICE = "price";
    String TAG_TOURDATE_NAME = "tour_date_name";

    String TAG_TOUR_NAME = "tour_name";
    String TAG_TOUR_DESCRIPTION = "tour_description";
    String TAG_TOUR_QUANTITY_NIGHT = "quantity_night";
    String TAG_TOUR_OPERATOR = "tour_operator";

    String TAG_PERSON_NAME = "name";
    String TAG_PERSON_SURNAME = "surname";
    String TAG_PERSON_PATRONYMIC = "patronymic";
    String TAG_PERSON_EMAIL = "email";
    String TAG_PERSON_TELNUM = "tel_num";
    String TAG_PERSON_ADDRESS = "address";
    String TAG_PERSON_BIRTHDATE = "birth_date";
    String TAG_PERSON_DOCUMENT = "document";

    String TAG_CONTACT_ORDER_EMAIL = "order_email";
    String TAG_CONTACT_ORDER_TELNUM = "order_tel_num";

    String TAG_CONTACTMANAGER_VOUCHER_EMAIL = "voucher_email";
    String TAG_CONTACTMANAGER_VOUCHER_TELNUM = "voucher_tel_num";

    String XML_FILE_Voucherlist = "voucherlist.xml";
    String XSD_FILE_Voucherlist = "VoucherList.xsd";
    String XML_FILE_Voucherlist_invalid = "voucherlist_invalid.xml";
    String XSD_FILE_Voucherlist_invalid = "voucherlist_invalid.xsd";
    Class<?> OBJECT_FACTORY = ua.silentium.entity.vouchers.ObjectFactory.class;

    String VOUCHERS_NAMESPACE_URI = "http://silentium.ua/entity/vouchers";
    String SCHEMA_LOCATION__ATTR_NAME = "schemaLocation";
    String SCHEMA_LOCATION__ATTR_FQN = "xsi:schemaLocation";
    String XSI_SPACE__PREFIX = "xsi";
    String SMV__URI = "http://silentium.ua/entity/vouchers";
    String SCHEMA_LOCATION__URI = "http://silentium.ua/entity/vouchers VoucherList.xsd";

    // validation features
    public static final String FEATURE__TURN_VALIDATION_ON = "http://xml.org/sax/features/validation";
    public static final String FEATURE__TURN_SCHEMA_VALIDATION_ON = "http://apache.org/xml/features/validation/schema";

}