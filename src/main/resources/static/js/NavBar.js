console.log("navbar.js has been loaded");

document.addEventListener("DOMContentLoaded", function () {
    // Fetch user role from the backend
    fetch("/api/user/role")
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to fetch role");
            }
            return response.json();
        })
        .then(data => {
            const userRole = data.role || "guest"; // Default to guest if no role is found
            updateNavbar(userRole);
            setupDropdown(); // Initialize dropdown AFTER navbar is inserted
        })
        .catch(error => {
            console.error("Error fetching user role:", error);
            updateNavbar("guest"); // Fallback to guest navbar
            setupDropdown();
        });
});

// Function to update the navbar based on the role
function updateNavbar(role) {
    const navbar = {
        "User": `
            <nav>
                <a href="/">Home</a>
                <a href="/">Wine Search</a>
                <a href="/">About</a>

                <!-- Dropdown Menu -->
                <div class="dropdown" id="friendDropdown">
                    <button class="dropbtn" id="dropbtn">Friends</button>
                    <div class="dropdown-content" id="dropdownContent">
                        <a href="/friendPage">Your Profile</a>
                        <a href="/">Find Friends</a>
                    </div>
                </div>

                <a href="">Report Bugs</a>
                <a href="/">Logout</a> <!-- Replaces login with logout -->
            </nav>
        `,
        "admin": `
            <nav>
                <a href="index.html">Home</a>
                <a href="users.html">Manage Users</a>
                <a href="wines.html">Manage Wines</a>
                <a href="settings.html">Settings</a>
                <a href="logout.html">Logout</a>
            </nav>
        `,
        "guest": `
            <nav>
                <a href="index.html">Home</a>
                <a href="login.html">Login</a>
                <a href="about.html">About</a>
            </nav>
        `,
    };

    document.getElementById("navbar").innerHTML = navbar[role] || navbar["guest"];
}

// Function to initialize the dropdown menu functionality
function setupDropdown() {
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
}
