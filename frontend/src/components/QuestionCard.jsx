// src/components/QuestionCard.jsx
import React from "react";

function QuestionCard({ question, index, total, selectedOption, onSelect }) {
  return (
    <div className="card question-card">
      <div className="question-meta">
        <span className="question-index">
          Question {index + 1} of {total}
        </span>
      </div>

      <h2 className="question-text">{question.text}</h2>

      <div className="options-list">
        {question.options.map((opt) => (
          <button
            key={opt}
            type="button"
            className={
              "option-button" +
              (selectedOption === opt ? " option-button-selected" : "")
            }
            onClick={() => onSelect(opt)}
          >
            {opt}
          </button>
        ))}
      </div>
    </div>
  );
}

export default QuestionCard;
