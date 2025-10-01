document.addEventListener("DOMContentLoaded", () => {
  console.log("✅ app.js loaded");

  const API_URL = "/api/expenses";
  const SUMMARY_URL = "/api/expenses/summary"; 

  const expenseListEl = document.getElementById("expense-list");
  const monthSelect = document.getElementById("month-select");
  const yearSelect = document.getElementById("year-select");
  const showBtn = document.getElementById("show-month-btn");
  const monthlyTotalEl = document.getElementById("monthly-total");
  const monthlyTotalClientEl = document.getElementById("monthly-total-client");
  const selectedMonthEl = document.getElementById("selected-month");

  const months = ["Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"];
  months.forEach((m, idx) => {
    const opt = document.createElement("option");
    opt.value = idx + 1; 
    opt.textContent = `${m} (${idx+1})`;
    monthSelect.appendChild(opt);
  });

  const now = new Date();
  const thisYear = now.getFullYear();

  for (let y = thisYear - 5; y <= thisYear + 1; y++) {
    const opt = document.createElement("option");
    opt.value = y;
    opt.textContent = y;
    if (y === thisYear) opt.selected = true;
    yearSelect.appendChild(opt);
  }

  monthSelect.value = now.getMonth() + 1;

  async function fetchExpenses() {
    const res = await fetch(API_URL);
    if (!res.ok) throw new Error("Failed to load expenses");
    return res.json();
  }

  function fmtDate(dstr) {
    if (!dstr) return "";
    const d = new Date(dstr + "T00:00:00");
    if (isNaN(d)) return dstr;
    return d.toLocaleDateString();
  }

  async function loadExpenses() {
    try {
      const expenses = await fetchExpenses();
      expenseListEl.innerHTML = expenses.map(e => {
        const amt = typeof e.amount === "string" ? parseFloat(e.amount) : e.amount;
        return `
          <div class="exp-item">
            <div class="exp-left">
              <div class="exp-title">${escapeHtml(e.title || "—")}</div>
              <div class="exp-meta">${escapeHtml(e.category || "General")} • ${fmtDate(e.expenseDate)}</div>
            </div>
            <div class="exp-amt">₹ ${Number(amt).toFixed(2)}</div>
          </div>
        `;
      }).join("");

      computeClientMonthlyTotal(expenses);
    } catch (err) {
      console.error("Error loading expenses:", err);
      expenseListEl.innerHTML = "<div class='muted'>Unable to load expenses</div>";
    }
  }

  function escapeHtml(str) {
    if (!str) return "";
    return String(str).replace(/[&<>\"']/g, (s) => ({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[s]));
  }

  function computeClientMonthlyTotal(expenses) {
    const year = Number(yearSelect.value);
    const month = Number(monthSelect.value); 
    selectedMonthEl.textContent = `${months[month-1]} ${year}`;

    const total = expenses
      .filter(e => {
        if (!e.expenseDate) return false;
        const d = new Date(e.expenseDate + "T00:00:00");
        return d.getFullYear() === year && (d.getMonth() + 1) === month;
      })
      .reduce((sum, e) => sum + (Number(e.amount) || 0), 0);

    monthlyTotalClientEl.textContent = `₹ ${Number(total).toFixed(2)}`;

    fetchServerMonthlyTotal(year, month);
  }

  async function fetchServerMonthlyTotal(year, month) {
    try {
      const res = await fetch(`${SUMMARY_URL}?year=${year}&month=${month}`);
      if (!res.ok) throw new Error("Server summary fetch failed");
      const json = await res.json();
      const total = json.total ?? 0;
      monthlyTotalEl.textContent = `₹ ${Number(total).toFixed(2)}`;
    } catch (err) {
      console.warn("Server summary not available, using client total", err);
      monthlyTotalEl.textContent = monthlyTotalClientEl.textContent;
    }
  }

  showBtn.addEventListener("click", async () => {

    await loadExpenses();
  });

  document.getElementById("expense-form").addEventListener("submit", async (ev) => {
    ev.preventDefault();
    if (!confirm("Do you want to add this expense?")) {
      return;
    }

    const payload = {
      title: document.getElementById("title").value,
      amount: parseFloat(document.getElementById("amount").value) || 0,
      category: document.getElementById("category").value || "General",
      expenseDate: document.getElementById("date").value,  
      note: document.getElementById("note").value || null
    };

    try {
      const res = await fetch(API_URL, {
        method: "POST",
        headers: {"Content-Type":"application/json"},
        body: JSON.stringify(payload)
      });
      if (!res.ok) throw new Error("Add failed");

      document.getElementById("expense-form").reset();

      await loadExpenses();
      alert("Expense added successfully!");
    } catch (err) {
      console.error("Error adding expense:", err);
      alert("Could not add expense. See console for details.");
    }
  });

  document.getElementById("clear-btn").addEventListener("click", () => {
    document.getElementById("expense-form").reset();
  });

  loadExpenses();
});
