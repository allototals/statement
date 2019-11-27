/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.onefi.bankstatement.impl.utility;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author michael
 */
@Slf4j
public class NameMatcher {

    public static final int DEFAULT_MIN_MATCH = 2;
    public static final double MATCH_THRESHOLD = 0.85;
    public static final int MIN_NAME_LENGTH = 2;
    public static final String NAMES_SEPARATOR = "[^a-zA-Z0-9\\-]+";

    public static Set<String> breakString(String string) {
        return breakString(string, MIN_NAME_LENGTH);
    }

    public static Set<String> breakString(String string, int minLength) {
        if (StringUtils.isEmpty(string)) {
            return new TreeSet<>();
        }

        return new TreeSet<>(
                Arrays.asList(
                        StringUtils.lowerCase(StringUtils.trim(string)).split(NAMES_SEPARATOR)
                )
                        .stream()
                        .filter((s) -> s.length() >= minLength)
                        .collect(Collectors.toList())
        );
    }

    public static boolean match(String names1, String names2, int minMatches, int minNameLength) {
        log.debug("Name match test");

        if (StringUtils.isEmpty(names1) || StringUtils.isEmpty(names2)) {
            log.warn("Empty names included in match.");
            return false;
        }

        Set<String> nameSet1 = breakString(names1, minNameLength);
        log.trace("Name Set 1: " + nameSet1);

        Set<String> nameSet2 = breakString(names2, minNameLength);
        log.trace("Name Set 2: " + nameSet2);

        int matches = 0;

        for (String aName : nameSet1) {
            Iterator<String> set2Iterator = nameSet2.iterator();
            while (set2Iterator.hasNext()) {
                String anotherName = set2Iterator.next();
                double sim = StringUtils.getJaroWinklerDistance(aName, anotherName);
                log.trace("Distance between (" + aName + "," + anotherName + ") = " + sim);
                if (sim < MATCH_THRESHOLD) {
                    //try reverse comparison
                    //this is because JWD favors strings that start with similar text, 
                    //while most Nigerian names are shorten-ned in between, 
                    //e.g. Wale for Olawale
                    sim = StringUtils.getJaroWinklerDistance(StringUtils.reverse(aName), StringUtils.reverse(anotherName));
                    log.trace("Rev.Distance between (" + aName + "," + anotherName + ") = " + sim);
                }

                if (sim >= MATCH_THRESHOLD) {
                    matches++;
                    set2Iterator.remove();
                    break;
                }
            }

            if (matches >= minMatches) {
                return true;
            }
        }

        return false;
    }

    public static boolean match(String names1, String names2) {
        return match(names1, names2, DEFAULT_MIN_MATCH, MIN_NAME_LENGTH);
    }

    public static boolean match(String names1, String names2, int minMatches) {
        return match(names1, names2, minMatches, MIN_NAME_LENGTH);
    }
}
