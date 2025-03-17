document.addEventListener("DOMContentLoaded", function () {
    const dropdown = document.getElementById("friendDropdown");
    const dropdownContent = document.getElementById("dropdownContent");

    if (dropdown && dropdownContent) { // Ensure elements exist
        dropdown.addEventListener("mouseenter", () => {
            dropdownContent.style.display = "block";
        });

        dropdown.addEventListener("mouseleave", () => {
            dropdownContent.style.display = "none";
        });
    } else {
        console.error("Dropdown elements not found. Check your HTML structure.");
    }
});
