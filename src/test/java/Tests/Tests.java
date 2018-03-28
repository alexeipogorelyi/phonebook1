package Tests;

import PhoneBook.PhoneBook;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class Tests {

    private PhoneBook createPhoneBook() {
        PhoneBook phoneBook = new PhoneBook();

        phoneBook.addPerson("Mikle",
                new HashSet<>(Arrays.asList("79941233412", "*100#", "+79941233412")));
        phoneBook.addPerson("NIKE",
                new HashSet<>(Arrays.asList("79941233412", "*43243#", "+7(994)153-34-12")));
        phoneBook.addPerson("Adidas",
                new HashSet<>(Arrays.asList("79941233412", "1234567890")));
        return phoneBook;
    }

    @Test
    void addPerson() {
        PhoneBook phoneBook = new PhoneBook();
        phoneBook.addPerson("Mike",
                new HashSet<>(Arrays.asList("89999999999", "12312312312")));
        assertEquals(1, phoneBook.getStorage().size());
        phoneBook.addPerson("Mike",
                new HashSet<>(Arrays.asList("89999999999", "12312312312", "89998999999", "32112312312")));
        assertEquals(2, phoneBook.getStorage().get("Mike").size());
    }

    @Test
    void addPhone() {
        PhoneBook phoneBook = new PhoneBook();
        phoneBook.addPerson("Mike", new HashSet<>(Arrays.asList("89999999999", "12312312312")));
        phoneBook.addPhone("Mike", "1233213232");
        assertEquals(3, phoneBook.getStorage().get("Mike").size());
        assertFalse(phoneBook.addPhone("NIKE", "1233213232"));
    }

    @Test
    void removePerson() {
        PhoneBook phoneBook = createPhoneBook();
        phoneBook.removePerson("Mikle");
        assertEquals(2, phoneBook.getStorage().size());
        assertFalse(phoneBook.removePerson("Mikle"));
    }

    @Test
    void removePhone() {
        PhoneBook phoneBook = createPhoneBook();
        phoneBook.removePhone("Mikle", "79941233412");
        assertEquals(2, phoneBook.getStorage().get("Mikle").size());
        assertFalse(phoneBook.removePhone("Mikle", "79941233412"));
    }

    @Test
    void findOfPersonName() {
        PhoneBook phoneBook = createPhoneBook();
        assertEquals(new HashSet<>(Arrays.asList("79941233412", "*100#", "+79941233412")),
                phoneBook.findOfPersonName("Mikle"));
        assertThrows(IllegalArgumentException.class, () -> {
            phoneBook.findOfPersonName("Mike");
        });
    }

    @Test
    void findOfPhone() {
        PhoneBook phoneBook = createPhoneBook();
        assertEquals(Arrays.asList("NIKE", "Mikle", "Adidas"),
                phoneBook.findOfPhone("79941233412"));
        assertThrows(IllegalArgumentException.class, () -> {
            phoneBook.findOfPhone("4564564545");
        });
    }
}