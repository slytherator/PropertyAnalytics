package analysis.property;

import com.google.code.externalsorting.csv.CsvExternalSort;
import com.google.code.externalsorting.csv.CsvSortOptions;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.List;

public class SortFile {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Comparator<CSVRecord> comparator = (op1, op2) -> op1.get(2).compareTo(op2.get(2));

        CsvSortOptions sortOptions = new CsvSortOptions
                .Builder(CsvExternalSort.DEFAULTMAXTEMPFILES, comparator, 1, CsvExternalSort.estimateAvailableMemory())
                .charset(Charset.defaultCharset())
                .distinct(false)
                .numHeader(1)
                .skipHeader(false)
                .format(CSVFormat.DEFAULT)
                .build();

        File file = new File("[DATA_PATH]/pp-complete.csv");
        File outputfile = new File("[DATA_PATH]/pp-complete-sorted.csv");
        List<File> sortInBatch = CsvExternalSort.sortInBatch(file, null, sortOptions);
        CsvExternalSort.mergeSortedFiles(sortInBatch, outputfile, sortOptions, true);
    }
}
