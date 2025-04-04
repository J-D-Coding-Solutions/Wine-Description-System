function generateTable(data) {
    const tableBody = document.getElementById("wineTableBody");
    tableBody.innerHTML = ''; // Clear old table data

    // Populate the table with new data
    const headers = Object.keys(data[0]);

    data.forEach(item => {
        const row = document.createElement("tr");
        headers.forEach(key => {
            const cell = document.createElement("td");
            cell.textContent = item[key]; // Fill data from JSON
            row.appendChild(cell);
        });
        tableBody.appendChild(row);
    });

    const table = document.getElementById("wineTable");
    table.style.display = "block";
}



async function submitWineDescription() {
    const wineDescription = document.getElementById("wineDescription").value;
    const wineCountry = document.getElementById("wineCountry").value;
    const wineProvince = document.getElementById("wineProvince").value;
    const wineVariety = document.getElementById("wineVariety").value;


    //const combinedInput = `Description: ${wineDescription}, Country: ${wineCountry}, Province: ${wineProvince}`;
    const combinedInput = `${wineDescription} ${wineCountry} ${wineProvince} ${wineVariety}`;

    const wineData = { combinedInput: combinedInput }; // Correctly formatted object

    const response = await fetch("http://localhost:8080/api/Wine-Description", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(wineData), // Send directly, not nested
    });

    const data = await response.json();
    console.log(data);

    if(!(data.length === 0)) {generateTable(data);}
    else {    const tableBody = document.getElementById("wineTableBody");
        tableBody.innerHTML = 'NO MATCHING WINES!';
        tableBody.style.color = "white";
        const table = document.getElementById("wineTable");
        table.style.display = "block";}// Clear old table data}
}
