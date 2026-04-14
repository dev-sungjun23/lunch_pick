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
    <form onSubmit={onSubmit} style={{ marginTop: 16 }}>
      <h2>투표</h2>
      <div>
        <label>
          팀원{' '}
          <select value={memberId} onChange={(e) => onMemberIdChange(e.target.value)} required>
            <option value="">선택</option>
            {members.map((m) => (
              <option key={m.id} value={m.id}>
                {m.name}
              </option>
            ))}
          </select>
        </label>
      </div>
      <button type="submit" disabled={!canVote || loadingVote}>
        투표하기
      </button>
      {!selectedRestaurantId && candidatesLength > 0 && (
        <span style={{ marginLeft: 8, color: '#666' }}>후보를 먼저 선택하세요</span>
      )}
      {voteOk && <p style={{ color: 'green' }}>{voteOk}</p>}
    </form>
  );
}
