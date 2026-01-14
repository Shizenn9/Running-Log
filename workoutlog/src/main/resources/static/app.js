// ====== DOM ELEMENTS ======
const list = document.getElementById("workoutList");
const form = document.getElementById("workoutForm");
const date = document.getElementById("date");
const distance = document.getElementById("distance");
const pace = document.getElementById("pace");
const totalDistanceEl = document.getElementById("totalDistance");
const progressEl = document.getElementById("progress");
const authMessage = document.getElementById("authMessage");

// ====== AUTH: REGISTER ======
const registerForm = document.getElementById("registerForm");
if (registerForm) {
  registerForm.addEventListener("submit", async (e) => {
    e.preventDefault();

    const username = document.getElementById("regUsername").value;
    const password = document.getElementById("regPassword").value;

    const res = await fetch("http://localhost:8080/register", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ username, password })
    });

    const text = await res.text();
    authMessage.textContent = text;
    authMessage.style.color = res.ok ? "#22c55e" : "#ef4444";
  });
}

// ====== AUTH: LOGIN ======
const loginForm = document.getElementById("loginForm");
if (loginForm) {
  loginForm.addEventListener("submit", async (e) => {
    e.preventDefault();

    const username = document.getElementById("loginUsername").value;
    const password = document.getElementById("loginPassword").value;

    const res = await fetch("http://localhost:8080/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ username, password })
    });

    const text = await res.text();
    authMessage.textContent = text;
    authMessage.style.color = res.ok ? "#22c55e" : "#ef4444";
  });
}

// ====== LOAD WORKOUTS ======
function loadWorkouts() {
  fetch("/workouts")
    .then(res => res.json())
    .then(data => {
      list.innerHTML = "";
      let totalDistance = 0;

      data.forEach(w => {
        const li = document.createElement("li");

        const text = document.createElement("span");
        text.textContent = `${w.date} — ${w.distance} km @ ${w.pace}`;

        const editBtn = document.createElement("button");
        editBtn.textContent = "✏️";
        editBtn.classList.add("delete-btn");

        const deleteBtn = document.createElement("button");
        deleteBtn.textContent = "❌";
        deleteBtn.classList.add("delete-btn");

        // EDIT
        editBtn.onclick = () => {
          li.innerHTML = "";

          const dateInput = document.createElement("input");
          dateInput.value = w.date;

          const distInput = document.createElement("input");
          distInput.type = "number";
          distInput.value = w.distance;

          const paceInput = document.createElement("input");
          paceInput.value = w.pace;

          const saveBtn = document.createElement("button");
          saveBtn.textContent = "💾";
          saveBtn.classList.add("delete-btn");

          saveBtn.onclick = () => {
            fetch(`/workouts/${w.id}`, {
              method: "PUT",
              headers: { "Content-Type": "application/json" },
              body: JSON.stringify({
                date: dateInput.value,
                distance: distInput.value,
                pace: paceInput.value
              })
            }).then(loadWorkouts);
          };

          li.append(dateInput, distInput, paceInput, saveBtn);
        };

        // DELETE
        deleteBtn.onclick = () => {
          if (confirm("Delete this workout?")) {
            fetch(`/workouts/${w.id}`, { method: "DELETE" })
              .then(loadWorkouts);
          }
        };

        li.append(text, editBtn, deleteBtn);
        list.appendChild(li);

        totalDistance += parseFloat(w.distance);
      });

      totalDistanceEl.textContent = totalDistance.toFixed(1);
      const progressPercent = Math.min((totalDistance / 50) * 100, 100);
      progressEl.style.width = progressPercent + "%";
    });
}

// ====== ADD WORKOUT ======
if (form) {
  form.addEventListener("submit", (e) => {
    e.preventDefault();

    fetch("/workouts", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        date: date.value,
        distance: distance.value,
        pace: pace.value
      })
    }).then(() => {
      form.reset();
      loadWorkouts();
    });
  });
}

// ====== INIT ======
loadWorkouts();
