export default function MemberForm({ memberName, onMemberNameChange, onSubmit, loading }) {
  return (
    <form onSubmit={onSubmit} style={{ display: 'flex', flexDirection: 'column', gap: 12 }}>
      <div>
        <label>
          팀원 이름{' '}
          <input
            value={memberName}
            onChange={(e) => onMemberNameChange(e.target.value)}
            required
            style={{ padding: 8, width: '100%', boxSizing: 'border-box' }}
            placeholder="ex) 홍길동"
          />
        </label>
      </div>
      <button
        type="submit"
        disabled={loading}
        style={{
          padding: '8px 12px',
          borderRadius: 8,
        }}
      >
        팀원 추가
      </button>
    </form>
  );
}
