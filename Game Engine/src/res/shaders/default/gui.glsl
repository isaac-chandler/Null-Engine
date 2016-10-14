#VS
#version 150 core

in vec3 inPosition;
in vec2 inTexCoords;

out vec2 texCoords;

uniform mat4 mvp;

uniform vec2 pos;
uniform vec2 size;

void main() {
	gl_Position = mvp * vec4(inPosition.xy * size + pos, 0, 1);
	texCoords = inTexCoords;
}
#VS

#FS
#version 150 core

in vec2 texCoords;

out vec4 outColor;

uniform sampler2D sampler;
uniform vec4 color;

void main() {
	outColor = texture(sampler, texCoords) * color;
}
#FS
