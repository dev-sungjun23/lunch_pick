export default function VoteActions({
  members,
  memberId,
  onMemberIdChange,
  selectedRestaurantId,
  candidatesLength,
  onSubmit,
  loadingVote,
  voteOk,
}) {
  const canVote =
    selectedRestaurantId != null &&
    memberId !== '' &&
    !Number.isNaN(Number(memberId));

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
      <h2 style={{ margin: 0 }}>투표</h2>
      <div>
        <label>
          팀원{' '}
          <select
            value={memberId}
            onChange={(e) => onMemberIdChange(e.target.value)}
            required
            style={{ padding: 8, minWidth: 160 }}
          >
            <option value="">선택</option>
            {members.map((m) => (
              <option key={m.id} value={m.id}>
                {m.name}
              </option>
            ))}
          </select>
        </label>
      </div>
      <button
        type="submit"
        disabled={!canVote || loadingVote}
        style={{
          alignSelf: 'flex-start',
          padding: '8px 12px',
          borderRadius: 4,
          border: '1px solid #ccc',
          background: !canVote || loadingVote ? '#eee' : '#f5f5f5',
          cursor: !canVote || loadingVote ? 'default' : 'pointer',
        }}
      >
        투표하기
      </button>
      {!selectedRestaurantId && candidatesLength > 0 && (
        <span style={{ fontSize: 12, color: '#666' }}>후보를 먼저 선택하세요</span>
      )}
      {voteOk && <p style={{ color: 'green', margin: 0 }}>{voteOk}</p>}
    </form>
  );
}
