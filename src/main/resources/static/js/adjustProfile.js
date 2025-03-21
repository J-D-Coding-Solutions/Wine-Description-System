function searchFriend(){
    var input, filter, table, tr, td, i, txtValue;
    input = document.getElementById("searchFriend");
    filter = input.value.toUpperCase();
    table = document.getElementById("userTable");
    tr = table.getElementsByTagName("tr");

    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[1];
        if (td) {
          txtValue = td.textContent || td.innerText;
          if (txtValue.toUpperCase().indexOf(filter) > -1) {
            tr[i].style.display = "";
          } else {
            tr[i].style.display = "none";
          }
        }
      }
}

function deleteUser(thisObj){
    var confrimDelete = confirm("Are you sure you want to delete this user?");
    if (confrimDelete){
        thisObj.remove();
    }
}