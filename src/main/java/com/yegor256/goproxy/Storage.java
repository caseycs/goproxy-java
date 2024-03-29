/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.yegor256.goproxy;

import com.jcabi.log.Logger;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * The storage.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public interface Storage {

    /**
     * Fetch one file.
     * @param key The key
     * @param path The path to load into
     * @throws IOException If fails
     */
    void load(String key, Path path) throws IOException;

    /**
     * Simple storage, in files.
     *
     * @since 0.1
     */
    final class Simple implements Storage {
        /**
         * Where we keep the data.
         */
        private final Path dir;
        /**
         * Ctor.
         * @throws IOException If fails
         */
        public Simple() throws IOException {
            this(Files.createTempDirectory("rpm-files"));
        }
        /**
         * Ctor.
         * @param path The path to the dir
         */
        public Simple(final Path path) {
            this.dir = path;
        }
        @Override
        public void load(final String key, final Path path) throws IOException {
            final Path source = Paths.get(this.dir.toString(), key);
            Files.copy(source, path, StandardCopyOption.REPLACE_EXISTING);
            Logger.info(
                this,
                "Loaded %d bytes of %s: %s",
                Files.size(source), key, source
            );
        }
    }
}
