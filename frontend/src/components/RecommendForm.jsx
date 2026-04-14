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
    <form
      onSubmit={onSubmit}
      style={{
        marginTop: 16,
        padding: 16,
        borderRadius: 8,
        border: '1px solid #eee',
        background: '#fafafa',
        display: 'flex',
        flexDirection: 'column',
        gap: 12,
      }}
    >
      <h2 style={{ margin: 0 }}>조건</h2>
      <div>
        <label>
          위치{' '}
          <input
            value={location}
            onChange={(e) => onLocationChange(e.target.value)}
            required
            style={{ padding: 8, width: '100%', boxSizing: 'border-box' }}
          />
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
            style={{ padding: 8, width: '100%', boxSizing: 'border-box' }}
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
            style={{ padding: 8, width: '100%', boxSizing: 'border-box' }}
          />
        </label>
      </div>
      <button
        type="submit"
        disabled={loading}
        style={{
          alignSelf: 'flex-start',
          padding: '8px 12px',
          borderRadius: 8,
        }}
      >
        후보 3개 받기
      </button>
    </form>
  );
}
