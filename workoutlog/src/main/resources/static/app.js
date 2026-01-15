// LOGIN
const loginForm = document.getElementById("loginForm");
if (loginForm) {
  loginForm.addEventListener("submit", async (e) => {
    e.preventDefault();

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    const res = await fetch("http://localhost:8080/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({ username, password })
    });

    const message = document.getElementById("message");

    if (res.ok) {
      message.innerText = "Login successful ✅ Redirecting...";
      message.style.color = "lightgreen";
      setTimeout(() => {
        window.location.href = "index.html";
      }, 800);  
    } else {
      message.innerText = "Invalid username or password";
      message.style.color = "salmon";
    }
  });
}

// REGISTER
const registerForm = document.getElementById("registerForm");
if (registerForm) {
  registerForm.addEventListener("submit", async (e) => {
    e.preventDefault();

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    const res = await fetch("http://localhost:8080/register", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({ username, password })
    });

    const message = document.getElementById("message");

    if (res.ok) {
      const username = await res.text();
      localStorage.setItem("username", username);

      message.innerText = "Login successful";
      message.style.color = "lightgreen";

      setTimeout(() => {
      window.location.href = "index.html";
      }, 500);
    } else {
      message.innerText = "Invalid username or password";
      message.style.color = "salmon";
    }
  });

  const username = localStorage.getItem("username");
    if (username) {
      document.getElementById("welcome").innerText = `Welcome back, ${username} 👋`;
    }

}
