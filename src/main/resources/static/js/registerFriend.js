function requestSent(){
    var userName = document.getElementById("friendName").value;
    if(userName == "" || userName == null){
        alert("Text field is empty!");}
    else{
        alert("Friend request sent successfully");
    }
}