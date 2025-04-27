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
    const wineWinery = document.getElementById("wineWinery").value;


    //const combinedInput = `Description: ${wineDescription}, Country: ${wineCountry}, Province: ${wineProvince}`;
    const combinedInput = `${wineDescription} ${wineCountry} ${wineProvince} ${wineVariety} ${wineWinery}`;

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

const provincesByCountry = {
    "United States of America": ["California", "Oregon", "Washington", "Idaho", "New York", "Virginia"],
    "Spain": ["Northern Spain", "Galicia", "Andalucia", "Levante", "Spain Other"],
    "France": ["Provence", "Southwest France", "Rhone Valley", "Burgundy", "Loire Valley", "Champagne", "Languedoc-Roussillon", "Bordeaux", "Alsace", "France Other"],
    "Italy": ["Northeastern Italy", "Tuscany", "Piedmont", "Veneto", "Sicily & Sardinia", "Southern Italy", "Lombardy" ,"Italy Other"],
    "New Zealand": ["Kumeu"],
    "Bulgaria": ["Bulgaria"],
    "Argentina": ["Mendoza Province"],
    "Australia": ["Victoria", "South Australia", "Tasmania"],
    "Portugal": ["Alentejano", "Alentejo", "Beira Atlantico", "Douro", "Tejo", "Vinho Verde", "Dao", "Duriense", "Beiras", "Lisboa", "Terras do Dao", "Bairrada", "Portuguese Table Wine", "Peninsula de Setubal"],
    "Israel": ["Upper Galilee", "Golan Heights", "Galilee", "Judean Hills"],
    "South Africa": ["Stellenbosch", "Walker Bay", "Western Cape", "Overberg", "Robertson", "Simonsberg-Paarl"],
    "Greece": ["Atalanti Valley", "Santorini", "Florina", "Nemea", "Peloponnese"],
    "Chile": ["Marchigue", "Marchigue", "Curicó Valley", "Maule Valley", "Maipo Valley", "Casablanca Valley", "Cachapoal Valley", "Leyda Valley", "Peumo", "Limarí Valley", "Aconcagua Valley" ],
    "Morocco": ["Guerrouane"],
    "Romania": ["Colinele Dobrogei", "Dealurile Munteniei"],
    "Germany": ["Mosel", "Rheinhessen", "Württemberg", "Ahr", "Rheingau", "Nahe", "Baden"],
    "Canada": ["British Columbia", "Ontario"],
    "Hungary": ["Tokaji"],
    "Austria": ["Thermenregion", "Burgenland", "Carnuntum"],
    "Croatia": ["North Dalmatia"],
    "Bulgaria": ["Thracian Valley"],
    "Slovenia": ["Goriska Brda"],
    "Lebanon": ["Lebanon"],
    "Macedonia": ["Macedonia"],
    "Georgia": ["Georgia"],
    "Serbia": ["Serbia"],
    "Turkey": ["Turkey"],
    "Egypt": ["Egypt"],
    "China": ["China"],
    "Japan": ["Japan"],
    "Lithuania": ["Lithuania"],
    "Ukraine": ["Ukraine"],
    "Tunisia": ["Tunisia"],
    "Moldova": ["Moldova"],
    "Other": ["Other"]

}

function filterProvinces(){
    const provinceSelect = document.getElementById("wineProvince");
    const country = document.getElementById("wineCountry");

    const selectedCountry = country.value;
    provinceSelect.innerHTML = '<option value="All">Select a province/state</option>';
    if(selectedCountry && provincesByCountry[selectedCountry]  && selectedCountry !== "All"){
        provincesByCountry[selectedCountry].forEach(province => {
            provinceSelect.innerHTML += '<option value="' + province + '">' + province + '</option>';
        });
    }
    else{
        Object.values(provincesByCountry).forEach(country => {
            country.forEach(province => {
                provinceSelect.innerHTML += '<option value="' + province + '">' + province + '</option>';
            })
        })
    }

}

function fillProvince(){
    const provinceSelect = document.getElementById("wineProvince");
    Object.values(provincesByCountry).forEach(country => {
        country.forEach(province => {
            provinceSelect.innerHTML += '<option value="' + province + '">' + province + '</option>';
        })
    });
}