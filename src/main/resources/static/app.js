const locationList =
    document.getElementById("locationList");


const homeSection =
    document.getElementById("homeSection");


const queueSection =
    document.getElementById("queueSection");


const backButton =
    document.getElementById("backButton");


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


const bestTimeText =
    document.getElementById("bestTimeText");



/* =====================================================
   LOAD LOCATIONS
   ===================================================== */

async function loadLocations() {

    try {

        const response =
            await fetch("/api/locations");


        if (!response.ok) {

            throw new Error(
                "Failed to load locations"
            );

        }


        const locations =
            await response.json();


        locationList.innerHTML = "";


        if (locations.length === 0) {

            locationList.innerHTML =
                "<p>No locations available.</p>";

            return;

        }


        locations.forEach(location => {


            const card =
                document.createElement("div");


            card.className =
                "location-card";


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


            card.addEventListener(
                "click",
                () => {

                    showQueueStatus(location);

                }
            );


            locationList.appendChild(card);

        });


    } catch (error) {

        locationList.innerHTML =
            "<p>Unable to load locations.</p>";

        console.error(error);

    }

}



/* =====================================================
   SHOW QUEUE STATUS
   ===================================================== */

async function showQueueStatus(location) {

    try {


        const response =
            await fetch(
                `/api/queue/${location.id}`
            );


        if (!response.ok) {

            throw new Error(
                "Failed to load queue status"
            );

        }


        const queue =
            await response.json();



        /* ---------------------------------------------
           LOCATION INFORMATION
           --------------------------------------------- */

        queueLocationName.textContent =
            location.name;


        queueLocationType.textContent =
            `${location.type} • ${location.address}`;



        /* ---------------------------------------------
           QUEUE INFORMATION
           --------------------------------------------- */

        estimatedWait.textContent =
            Math.round(
                queue.estimatedWaitTime
            );


        peopleWaiting.textContent =
            queue.peopleWaiting;


        currentlyServing.textContent =
            queue.currentlyServing;


        averageService.textContent =
            queue.averageServiceTime;


        crowdBadge.textContent =
            `${queue.crowdStatus} CROWD`;


        updatedAt.textContent =
            formatUpdatedTime(
                queue.updatedAt
            );



        /* ---------------------------------------------
           HISTORICAL DATA
           --------------------------------------------- */

        await loadHistory(location.id);



        /* ---------------------------------------------
           BEST TIME INSIGHT
           --------------------------------------------- */

        await loadBestTime(location.id);



        /* ---------------------------------------------
           SHOW QUEUE PAGE
           --------------------------------------------- */

        homeSection.classList.add(
            "hidden"
        );


        queueSection.classList.remove(
            "hidden"
        );


        window.scrollTo(
            0,
            0
        );


    } catch (error) {

        alert(
            "Unable to load queue information."
        );

        console.error(error);

    }

}



/* =====================================================
   LOAD SERVICE HISTORY
   ===================================================== */

async function loadHistory(locationId) {

    try {


        const response =
            await fetch(
                `/api/history/${locationId}`
            );


        if (!response.ok) {

            throw new Error(
                "Failed to load history"
            );

        }


        const history =
            await response.json();


        historyList.innerHTML = "";


        if (history.length === 0) {

            historyList.innerHTML =
                "<p>No historical data available.</p>";

            return;

        }



        history.forEach(record => {


            const item =
                document.createElement("div");


            item.className =
                "history-item";


            item.innerHTML = `

                <div>

                    <strong>
                        ${record.peopleServed}
                        people served
                    </strong>

                    <p>
                        ${formatHistoryDate(
                            record.servedAt
                        )}
                    </p>

                </div>


                <div class="history-duration">

                    ${record.serviceDuration}
                    min

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



/* =====================================================
   LOAD BEST TIME
   ===================================================== */

async function loadBestTime(locationId) {

    try {


        bestTimeText.textContent =
            "Analyzing historical service activity...";


        const response =
            await fetch(
                `/api/best-time/${locationId}`
            );


        if (!response.ok) {

            throw new Error(
                "Failed to load best-time insight"
            );

        }


        const result =
            await response.json();



        if (result.enoughData) {

            bestTimeText.textContent =
                `${result.bestTimePeriod} has historically shown faster service.`;

        } else {

            bestTimeText.textContent =
                "More historical data is needed to recommend a time.";

        }


    } catch (error) {

        bestTimeText.textContent =
            "Best-time insight is currently unavailable.";

        console.error(error);

    }

}



/* =====================================================
   FORMAT QUEUE UPDATED TIME
   ===================================================== */

function formatUpdatedTime(timestamp) {

    const date =
        new Date(timestamp);


    return date.toLocaleTimeString(
        [],
        {
            hour: "2-digit",
            minute: "2-digit"
        }
    );

}



/* =====================================================
   FORMAT HISTORY DATE
   ===================================================== */

function formatHistoryDate(timestamp) {

    const date =
        new Date(timestamp);


    return date.toLocaleDateString(
        [],
        {
            day: "2-digit",
            month: "short",
            year: "numeric"
        }
    );

}



/* =====================================================
   BACK BUTTON
   ===================================================== */

backButton.addEventListener(
    "click",
    () => {


        queueSection.classList.add(
            "hidden"
        );


        homeSection.classList.remove(
            "hidden"
        );


        window.scrollTo(
            0,
            0
        );

    }
);



/* =====================================================
   INITIAL LOAD
   ===================================================== */

loadLocations();