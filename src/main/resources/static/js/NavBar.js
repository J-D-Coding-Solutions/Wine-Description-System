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
                <a href="/Dash">Home</a>
                <a href="/WineSearch">Wine Search</a>
                <a href="/WineRequest">Wine Request</a>

                <!-- Dropdown Menu -->
                <div class="dropdown" id="friendDropdown">
                    <button class="dropbtn" id="dropbtn">Friends</button>
                    <div class="dropdown-content" id="dropdownContent">
                        <a href="/friendPage">Your Profile</a>
                        <a href="/FriendRequest">Find Friends</a>
                    </div>
                </div>

                <a href="/ReportBuggies">Report Bugs</a>
                <button class="dropbtn" onclick="logout()">Logout</button>

            </nav>
        `,
        "Stakeholder": `
            <nav>
                <a href="/Dash">Home</a>
                <a href="/WineSearch">Wine Search</a>
                <a href="/WineRequest">Wine Request</a>
                <!-- Dropdown Menu -->
                <div class="dropdown" id="friendDropdown">
                    <button class="dropbtn" id="dropbtn">Friends</button>
                    <div class="dropdown-content" id="dropdownContent">
                        <a href="/friendPage">Your Profile</a>
                        <a href="/FriendRequest">Find Friends</a>
                    </div>
                </div>

                <a href="/ReportBuggies">Report Bugs</a>
                <button class="dropbtn" onclick="logout()">Logout</button>
            </nav>
        `,
        "guest": `
            <nav>
                <a href="/WineSearch">Wine Search</a>
                <a href="/ReportBuggies">Report Bugs</a>
                <a href="/">Login</a> <!-- Replaces login with logout -->
            </nav>
        `,
        "Admin": `
            <nav>
                <a href="/Dash">Home</a>
                <a href="/WineSearch">Wine Search</a>
                <a href="/WineRequests">Requested Wines</a>
                <a href="/AdjustProfiles">Adjust Profiles</a>
                <a href="/RegisterAccounts">Register Accounts</a>

                <!-- Dropdown Menu -->
                <div class="dropdown" id="friendDropdown">
                    <button class="dropbtn" id="dropbtn">Friends</button>
                    <div class="dropdown-content" id="dropdownContent">
                        <a href="/friendPage">Your Profile</a>
                        <a href="/FriendRequest">Find Friends</a>
                    </div>
                </div>
                <a href="/ViewBugs">View Bug Reports</a>
                <a href="/ReportBuggies">Report Bugs</a>
                <button class="dropbtn" onclick="logout()">Logout</button>
            </nav>
        `,
        "Moderator": `
            <nav>
                <a href="/Dash">Home</a>
                <a href="/WineSearch">Wine Search</a>
                <a href="/AdjustProfiles">Adjust Profiles</a>


                <!-- Dropdown Menu -->
                <div class="dropdown" id="friendDropdown">
                    <button class="dropbtn" id="dropbtn">Friends</button>
                    <div class="dropdown-content" id="dropdownContent">
                        <a href="/friendPage">Your Profile</a>
                        <a href="/FriendRequest">Find Friends</a>
                    </div>
                </div>
                
                <a href="/ViewBugs">View Bug Reports</a>
                <a href="/ReportBuggies">Report Bugs</a>
                <button class="dropbtn" onclick="logout()">Logout</button>
            </nav>
        `,
        "Sommelier": `
            <nav>
                <a href="/Dash">Home</a>
                <a href="/WineSearch">Wine Search</a>
                <a href="/WineRequests">Requested Wines</a>
                <!-- Dropdown Menu -->
                <div class="dropdown" id="friendDropdown">
                    <button class="dropbtn" id="dropbtn">Friends</button>
                    <div class="dropdown-content" id="dropdownContent">
                        <a href="/friendPage">Your Profile</a>
                        <a href="/FriendRequest">Find Friends</a>
                    </div>
                </div>

                <a href="/ReportBuggies">Report Bugs</a>
                <button class="dropbtn" onclick="logout()">Logout</button>
            </nav>
        `

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