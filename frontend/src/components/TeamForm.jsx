export default function TeamForm({ teamName, onTeamNameChange, onSubmit, loading }) {
  return (
    <form onSubmit={onSubmit}>
      <div>
        <label>
          팀 이름{' '}
          <input
            value={teamName}
            onChange={(e) => onTeamNameChange(e.target.value)}
            required
          />
        </label>
      </div>
      <button type="submit" disabled={loading}>
        생성
      </button>
    </form>
  );
}
