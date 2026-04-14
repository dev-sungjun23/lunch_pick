export default function RecommendForm({
  location,
  onLocationChange,
  maxDistance,
  onMaxDistanceChange,
  budget,
  onBudgetChange,
  onSubmit,
  loading,
}) {
  return (
    <form onSubmit={onSubmit}>
      <h2>조건</h2>
      <div>
        <label>
          위치{' '}
          <input value={location} onChange={(e) => onLocationChange(e.target.value)} required />
        </label>
      </div>
      <div>
        <label>
          도보(분 이하){' '}
          <input
            type="number"
            min={1}
            value={maxDistance}
            onChange={(e) => onMaxDistanceChange(e.target.value)}
            required
          />
        </label>
      </div>
      <div>
        <label>
          예산(원 이하){' '}
          <input
            type="number"
            min={1}
            value={budget}
            onChange={(e) => onBudgetChange(e.target.value)}
            required
          />
        </label>
      </div>
      <button type="submit" disabled={loading}>
        후보 3개 받기
      </button>
    </form>
  );
}
