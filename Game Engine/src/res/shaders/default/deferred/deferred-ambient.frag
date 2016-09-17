#version 150 core

in vec2 texCoord;

out vec4 outColor;

uniform vec3 ambientColor;

uniform sampler2D colors;
uniform sampler2D positions;
uniform sampler2D normals;
uniform sampler2D specular;

void main() {
	vec4 color = texture(colors, texCoord);
	outColor = vec4(color.rgb * ambientColor, color.a);
}
