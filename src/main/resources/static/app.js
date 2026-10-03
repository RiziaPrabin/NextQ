const locationList = document.getElementById("locationList");

const homeSection = document.getElementById("homeSection");
const queueSection = document.getElementById("queueSection");

const backButton = document.getElementById("backButton");

const queueLocationName =
    document.getElementById("queueLocationName");

const queueLocationType =
    document.getElementById("queueLocationType");

const estimatedWait =
    document.getElementById("estimatedWait");

const peopleWaiting =
    document.getElementById("peopleWaiting");

const currentlyServing =
    document.getElementById("currentlyServing");

const averageService =
    document.getElementById("averageService");

const crowdBadge =
    document.getElementById("crowdBadge");

const updatedAt =
    document.getElementById("updatedAt");

const historyList =
    document.getElementById("historyList");


// Load all available locations
async function loadLocations() {

    try {

        const response = await fetch("/api/locations");

        if (!response.ok) {
            throw new Error("Failed to load locations");
        }

        const locations = await response.json();

        locationList.innerHTML = "";

        locations.forEach(location => {

            const card = document.createElement("div");

            card.className = "location-card";

            card.innerHTML = `
                <div class="location-name">
                    ${location.name}
                </div>

                <div class="location-type">
                    ${location.type}
                </div>

                <div class="location-address">
                    ${location.address}
                </div>
            `;

            card.addEventListener("click", () => {
                showQueueStatus(location);
            });

            locationList.appendChild(card);
        });

    } catch (error) {

        locationList.innerHTML =
            "<p>Unable to load locations.</p>";

        console.error(error);
    }
}


// Load current queue information
async function showQueueStatus(location) {

    try {

        const response =
            await fetch(`/api/queue/${location.id}`);

        if (!response.ok) {
            throw new Error("Failed to load queue status");
        }

        const queue = await response.json();

        queueLocationName.textContent =
            location.name;

        queueLocationType.textContent =
            `${location.type} • ${location.address}`;

        estimatedWait.textContent =
            Math.round(queue.estimatedWaitTime);

        peopleWaiting.textContent =
            queue.peopleWaiting;

        currentlyServing.textContent =
            queue.currentlyServing;

        averageService.textContent =
            queue.averageServiceTime;

        crowdBadge.textContent =
            `${queue.crowdStatus} CROWD`;

        updatedAt.textContent =
            formatUpdatedTime(queue.updatedAt);

        // Load historical information
        await loadHistory(location.id);

        homeSection.classList.add("hidden");

        queueSection.classList.remove("hidden");

        window.scrollTo(0, 0);

    } catch (error) {

        alert("Unable to load queue information.");

        console.error(error);
    }
}


// Load historical service information
async function loadHistory(locationId) {

    try {

        const response =
            await fetch(`/api/history/${locationId}`);

        if (!response.ok) {
            throw new Error("Failed to load history");
        }

        const history = await response.json();

        historyList.innerHTML = "";

        if (history.length === 0) {

            historyList.innerHTML =
                "<p>No historical data available.</p>";

            return;
        }

        history.forEach(record => {

            const item = document.createElement("div");

            item.className = "history-item";

            item.innerHTML = `
                <div>
                    <strong>
                        ${record.peopleServed} people served
                    </strong>

                    <p>
                        ${formatHistoryDate(record.servedAt)}
                    </p>
                </div>

                <div class="history-duration">
                    ${record.serviceDuration} min
                </div>
            `;

            historyList.appendChild(item);
        });

    } catch (error) {

        historyList.innerHTML =
            "<p>Unable to load historical data.</p>";

        console.error(error);
    }
}


// Format current queue update time
function formatUpdatedTime(timestamp) {

    const date = new Date(timestamp);

    return date.toLocaleTimeString([], {
        hour: "2-digit",
        minute: "2-digit"
    });
}


// Format historical record date
function formatHistoryDate(timestamp) {

    const date = new Date(timestamp);

    return date.toLocaleDateString([], {
        day: "2-digit",
        month: "short",
        year: "numeric"
    });
}


// Back to location selection
backButton.addEventListener("click", () => {

    queueSection.classList.add("hidden");

    homeSection.classList.remove("hidden");

    window.scrollTo(0, 0);
});


// Start application
loadLocations();