async function submitWineDescription() {
    const searchInput = document.getElementById("wineDescription").value;


    const response = await fetch("http://localhost:8080/api/Wine-Description", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({ description: searchInput }),
    });
    // Handle the response from the backend
    const data = await response.json();
    console.log(data);

}
