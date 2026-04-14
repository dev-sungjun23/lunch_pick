export default function MemberForm({ memberName, onMemberNameChange, onSubmit, loading }) {
  return (
    <form onSubmit={onSubmit}>
      <div>
        <label>
          이름{' '}
          <input
            value={memberName}
            onChange={(e) => onMemberNameChange(e.target.value)}
            required
          />
        </label>
      </div>
      <button type="submit" disabled={loading}>
        추가
      </button>
    </form>
  );
}
