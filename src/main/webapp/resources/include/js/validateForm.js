/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function isDateAfter(after, before) {
    after = after.split('-');
    before = before.split('-');

    if (after[0] > before[0]) return true;
    else if (after[0] === before[0]) {
        if (after[1] > before[1]) return true;
        else if (after[1] === before[1]) {
            if (after[2] > before[2]) return true;
            // At this point, we don't care if the after date is after 
            // the before date or not, because for this application,
            // times don't matter
        }
    }
    return false;
}

function checkDates() {
    var startDate = document.forms["randomNumberPlotForm"]["startDate"].value;
    var endDate = document.forms["randomNumberPlotForm"]["endDate"].value;
    if (!isDateAfter(endDate, startDate)) {
        document.getElementById("dateErrorMsg").innerHTML = 
                "Start date must precede end date.";
        return false;
    } else {
        document.getElementById("dateErrorMsg").innerHTML = "";
        return true;
    }    
}

function checkFloats() {
    var minimum = parseFloat(document.forms["randomNumberPlotForm"]["minimum"].value);
    var maximum = parseFloat(document.forms["randomNumberPlotForm"]["maximum"].value);
    //alert(minimum.toString() + " < " + maximum.toString() + " == " + (minimum < maximum).toString())
    if (!(minimum < maximum)) {
        document.getElementById("valueErrorMsg").innerHTML = 
                "Minimum must be less than maximum.";
        return false;
    } else {
        document.getElementById("valueErrorMsg").innerHTML = "";
        return true;
    }
}

function validateForm() {
    var datesOK = checkDates();
    //alert("datesOK==" + datesOK.toString())
    var floatsOK = checkFloats();
    //alert("floatsOK==" + floatsOK.toString())
    return datesOK && floatsOK;
}
