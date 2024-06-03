package scene;

import static org.junit.jupiter.api.Assertions.*;

class WriteJsonExampleTest {

        @org.junit.jupiter.api.Test
        void write() {
            WriteJsonExample writeJsonExample = new WriteJsonExample();
            writeJsonExample.write();
        }

        @org.junit.jupiter.api.Test
        void read() {
            WriteJsonExample writeJsonExample = new WriteJsonExample();
            writeJsonExample.read();
        }

}