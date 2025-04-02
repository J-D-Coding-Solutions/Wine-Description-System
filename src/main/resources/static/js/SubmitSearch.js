async function submitWineDescription() {
    const wineDescription = document.getElementById("wineDescription").value;
    const wineCountry = document.getElementById("wineCountry").value;
    const wineProvince = document.getElementById("wineProvince").value;

    //const combinedInput = `Description: ${wineDescription}, Country: ${wineCountry}, Province: ${wineProvince}`;
    const combinedInput = `${wineDescription}, ${wineCountry}, ${wineProvince}`;

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
}
