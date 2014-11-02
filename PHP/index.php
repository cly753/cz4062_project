<!DOCTYPE html>
<html>
<!-- <title>ccc</title> -->
<body>

<!--     <form action="index.php" method="POST">
        name  :
        <input type="text" name="name"><br>
        phone :
        <input type="text" name="phone"><br>
        <input type="submit">
    </form> -->

    <?php
    function show_Array($array, $depth) {
        print("<br>");
        foreach ($array as $key => $value) {
            for ($i = 0; $i < $depth; $i++) {
                print("....");
            }
            print("$key => ");
            if (gettype($value) == "object") {
                show_Array($value, $depth + 1);
            }
            else {  
                print("$value");
            }
            print("<br>");
        }
    }

    function make_table($title, $array) {
        print("<br>". $title. "<br>");
        print("<table border='1' style='width:50%'>");
        foreach ($array as $key => $value) {
            print("<tr>");
            print("<td>$key</td>");
            print("<td>$value</td>");
            print("</tr>");
        }
        print("</table>");
    }

    function save_to_file($file_name) {
        $file_path = ".\\";

        $contacts = fopen($file_path. $file_name. ".txt", "a+") or die("FAILED TO OPEN FILE");

        date_default_timezone_set('UTC');
        fwrite($contacts, "\n\n// -------- time: ". date('l jS \of F Y h:i:s A'). " --------");
        
        fwrite($contacts, "\n// from _POST");
        foreach ($_POST as $name => $phone_number) {
            fwrite($contacts, "\n". $name. " = ". $phone_number);
        }

        fwrite($contacts, "\n// from _GET");
        foreach ($_GET as $name => $phone_number) {
            fwrite($contacts, "\n". $name. " = ". $phone_number);
        }

        fclose($contacts);

        print("<br><br>contacts saved.");
    }

    make_table("from _POST", $_POST);
    make_table("from _GET", $_GET);

    save_to_file("contacts");
    ?>
</body>
</html>

<script>
    // var time = new Date().getTime();
    // setTimeout(function() {
    //     window.location.reload(true);
    // }, 2000);
</script>