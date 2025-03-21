function removeWine(thisObj){
    var parentRow = thisObj.parentNode.parentNode;
    var confirmWine = confirm("Are you sure you want to delete this wine?");
    if (confirmWine){
        parentRow.remove();
    }
}