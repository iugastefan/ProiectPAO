package schimb_valutar;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Optional;

final class ServiciuFisiere {
    static Optional<List<String>> citesteFisier(String locatie, String fisier) throws IOException {
        Path file = FileSystems.getDefault().getPath(locatie, fisier);
        if (Files.isReadable(file))
            return Optional.of(Files.readAllLines(file, Charset.forName("UTF-8")));
        return Optional.empty();
    }

    static void scrieFisierAppend(String locatie, String fisier, List<String> date) throws IOException {
        Path file = FileSystems.getDefault().getPath(locatie, fisier);
        Files.write(file,
                date,
                Charset.forName("UTF-8"), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    static void scrieFisierOverwrite(String locatie, String fisier, List<String> date) throws IOException {
        Path file = FileSystems.getDefault().getPath(locatie, fisier);
        Files.write(file,
                date,
                Charset.forName("UTF-8"), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
}
