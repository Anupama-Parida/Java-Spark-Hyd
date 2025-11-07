document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("registrationForm");
  const formContainer = document.getElementById("formContainer");
  const detailsContainer = document.getElementById("detailsContainer");
  const detailsOutput = document.getElementById("detailsOutput");
  const backBtn = document.getElementById("backBtn");

  // Handle form submission
  form.addEventListener("submit", (e) => {
    e.preventDefault();

    // Get form data
    const fullName = document.getElementById("fullname").value;
    const email = document.getElementById("email").value;
    const dob = document.getElementById("dob").value;
    const gender = document.getElementById("gender").value;
    const course = document.getElementById("course").value;
    const phone = document.getElementById("phone").value;
    const address = document.getElementById("address").value;

    // Fill output section
    detailsOutput.innerHTML = `
      <p><strong>Full Name:</strong> ${fullName}</p>
      <p><strong>Email:</strong> ${email}</p>
      <p><strong>Date of Birth:</strong> ${dob}</p>
      <p><strong>Gender:</strong> ${gender}</p>
      <p><strong>Course:</strong> ${course}</p>
      <p><strong>Phone:</strong> ${phone}</p>
      <p><strong>Address:</strong> ${address}</p>
    `;

    // Show details, hide form
    formContainer.style.display = "none";
    detailsContainer.style.display = "block";
  });

  // Handle back button
  backBtn.addEventListener("click", () => {
    detailsContainer.style.display = "none";
    formContainer.style.display = "block";
    form.reset();
  });
});
