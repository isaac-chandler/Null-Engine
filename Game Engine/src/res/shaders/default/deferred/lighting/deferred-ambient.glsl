#VS
#version 150 core

in vec3 inPosition;
in vec2 inTexCoord;

out vec2 texCoord;

void main() {
	gl_Position = vec4(inPosition, 1);
	texCoord = inTexCoord;
}
#VS

#FS
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
	float lightingAmount = texture(specular, texCoord).b;
	outColor = vec4(color.rgb * mix(vec3(1, 1, 1), ambientColor, lightingAmount), color.a);
}
#FS
