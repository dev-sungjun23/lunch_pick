export default function MembersList({ members }) {
  if (!members.length) return null;
  return (
    <div style={{ marginTop: 16 }}>
      <h2 style={{ margin: '0 0 4px' }}>팀원 목록</h2>
      <ul style={{ paddingLeft: 18, margin: 0 }}>
        {members.map((m) => (
          <li key={m.id}>
            {m.name} (id: {m.id})
          </li>
        ))}
      </ul>
    </div>
  );
}
