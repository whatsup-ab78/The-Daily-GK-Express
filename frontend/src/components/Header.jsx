// src/components/Header.jsx
import React from "react";
import { useLocation, Link } from "react-router-dom";

function Header() {
  const location = useLocation();

  return (
    <header className="header">
      <div className="header-left">
        <span className="logo-pill">DGK</span>
        <div>
          <h1 className="header-title">The Daily GK Express</h1>
          <p className="header-subtitle">
            India-focused current affairs booster
          </p>
        </div>
      </div>

      <nav className="header-nav">
        <Link
          to="/"
          className={`nav-link ${
            location.pathname === "/" ? "nav-link-active" : ""
          }`}
        >
          Home
        </Link>
        <Link
          to="/quiz"
          className={`nav-link ${
            location.pathname === "/quiz" ? "nav-link-active" : ""
          }`}
        >
          Quiz
        </Link>
      </nav>
    </header>
  );
}

export default Header;
