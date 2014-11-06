<!DOCTYPE html>
<html>
<body>
    <?php
        function save_to_file($file_name) {
            $file_path = ".\\";

            $contacts = fopen($file_path. $file_name. ".txt", "a+") or die("FAILED TO OPEN FILE");

            date_default_timezone_set('UTC');
            fwrite($contacts, "\n\n// -------- time: ". date('l jS \of F Y h:i:s A'). " --------");
            
            fwrite($contacts, "\n// from _POST");
            foreach ($_POST as $name => $phone_number) {
                fwrite($contacts, "\n". str_replace("_", " ", $name). " ". $phone_number);
            }

            fwrite($contacts, "\n// from _GET");
            foreach ($_GET as $name => $phone_number) {
                fwrite($contacts, "\n". str_replace("_", " ", $name). " ". $phone_number);
            }

            fclose($contacts);

            print("<br><br>Contacts saved.");
        }
        save_to_file("contacts");
    ?>
</body>
</html>