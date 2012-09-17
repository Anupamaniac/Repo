package com.rosettastone.succor.bandit;

import com.rosettastone.succor.model.bandit.ProductRights;
import com.rosettastone.succor.model.bandit.Range;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Converter for product rights.
 * Input JSON objects looks like
 * {
 *  "family: "application",
 *  "rs_language_code": "ENG",
 *  "content_ranges: ["1-4", "5-8"],
 * },
 *
 * Empty range equal to [0,0]
 */

public class ProductRightsConverter implements JsonConverter<ProductRights> {


    @Override
    public ProductRights convert(JSONObject json) throws JSONException {
        ProductRights rights = new ProductRights();
        rights.setLanguageCode(json.getString("rs_language_code"));
        rights.setFamily(json.getString("family"));
        if (!json.has("content_ranges")) {
            rights.setRange(new Range(0, 0));
            return rights;
        }
        // parse array of ranges
        JSONArray array = json.getJSONArray("content_ranges");
        Range ranges[] = new Range[array.length()];
        for (int i = 0; i < array.length(); i++) {
            String rangeStr = array.getString(i);
            int idx = rangeStr.indexOf('-');

            int lo = Integer.parseInt(rangeStr.substring(0, idx));
            int hi = Integer.parseInt(rangeStr.substring(idx+1, rangeStr.length()));
            ranges[i] = new Range(lo, hi);
        }
        // sort ranges.
        Arrays.sort(ranges, rangeComparator);
        // check that all intervals are sequential without holes.
        for(int i = 1; i < ranges.length; i++) {
            if (i > 0) {
                if (ranges[i-1].getHi() + 1  != ranges[i].getLow()) {
                    throw new JSONException("Illegal ranges.");
                }
            }
        }
        // join ranges into one big range
        Range range;
        if (ranges.length > 0) {
            range = new Range(ranges[0].getLow(), ranges[ranges.length-1].getHi());
        } else {
            range = new Range(0, 0);
        }
        rights.setRange(range);
        return rights;
    }

    /**
     * Ranges comparator for sorting by ascending order.
     */
    private static final Comparator<Range> rangeComparator = new Comparator<Range>() {

        @Override
        public int compare(Range r1, Range r2) {
            if (r1.getHi() <= r2.getLow()) {
                return -1;
            }
            if (r1.getLow() >= r2.getHi()) {
                return 1;
            }
            if (r1.getLow() == r2.getLow()) {
                return r1.getHi() - r2.getHi();
            }
            return r1.getLow() - r2.getLow();
        }
    };

}
